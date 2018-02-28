package com.xxl.job.executor.service;

import org.springframework.stereotype.Service;

@Service
public class DevOpsService {

    public boolean isPipelineRunning(String pipeline) {
        return false;
    }

    public void trigger(String pipeline) {

    }
}
