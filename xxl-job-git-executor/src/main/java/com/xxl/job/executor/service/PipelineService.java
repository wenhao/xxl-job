package com.xxl.job.executor.service;

import org.springframework.stereotype.Service;

@Service
public class PipelineService {

    public boolean isPipelineRunning(String pipeline) {
        return false;
    }

    public boolean runPipeline(String pipeline) {
        return true;
    }

}
