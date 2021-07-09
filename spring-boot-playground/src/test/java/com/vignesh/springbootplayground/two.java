package com.scb.dqmf.playground_test.tools.solacemonitoring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "solace")
@Data
public class SolaceMonitoringProps {
    private String router;
    private String vpn;
    private List<String> queues;
}
