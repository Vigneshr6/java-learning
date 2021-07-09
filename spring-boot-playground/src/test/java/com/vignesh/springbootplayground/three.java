package com.scb.dqmf.playground_test.tools.solacemonitoring;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueueMonitoringResponse {
    private String queue;
    private long count;
}
