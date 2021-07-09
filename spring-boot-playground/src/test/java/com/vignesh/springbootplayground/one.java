package com.scb.dqmf.playground_test.tools.solacemonitoring;

import joptsimple.internal.Strings;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@TestPropertySource("/solace-monitoring.properties")
@EnableConfigurationProperties(SolaceMonitoringProps.class)
public class SolaceMonitoringApp {

    @Autowired
    private SolaceMonitoringProps props;

    @Test
    public void testGetMsgCount() {
        RestTemplate restTemplate = getRestTemplate();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        final List<CompletableFuture<QueueMonitoringResponse>> futures = props.getQueues().stream().map(queue ->
                CompletableFuture.supplyAsync(() -> {
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(getQueueURL(queue), String.class);
                    final JSONObject json = new JSONObject(responseEntity.getBody());
                    return new QueueMonitoringResponse(queue, json.getJSONObject("data").getLong("currentSpooledMsgCount"));
                }, executorService)
        ).collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        final List<QueueMonitoringResponse> results = futures.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        Collections.sort(results, (a, b) -> {
            if (b.getCount() > a.getCount()) {
                return 1;
            } else if (a.getCount() > b.getCount()) {
                return -1;
            } else {
                return 0;
            }
        });
        final int maxQueueName = results.stream().mapToInt(data -> data.getQueue().length()).max().orElse(0) + 5;
        final int maxCountLen = results.stream().mapToInt(data -> String.valueOf(data.getCount()).length()).max().orElse(0);
        String box = String.format("+%s+", Strings.repeat('-', maxQueueName + maxCountLen + 5));
        System.out.println(box);
        IntStream.range(0, results.size()).forEach(i -> {
            final QueueMonitoringResponse data = results.get(i);
            System.out.printf("| %-" + maxQueueName + "s | %-" + maxCountLen + "d |\n", data.getQueue(), data.getCount());
            if (i < results.size() - 1) {
                System.out.println(Strings.repeat('-', maxQueueName + maxCountLen + 7));
            }
        });
        System.out.println(box);
    }


    private String getQueueURL(String queue) {
        return "http://" + props.getRouter() + ".hk.standardchartered.com/SEMP/v2/__private_monitor__/msgVpns/" + props.getVpn() + "/queues/" + queue + "?select=msgVpnName,queueName,currentSpooledMsgCount";
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("fssreader", "fssreader"));
        return restTemplate;
    }
}
