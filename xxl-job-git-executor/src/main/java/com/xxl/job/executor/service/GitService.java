package com.xxl.job.executor.service;

import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import org.apache.commons.exec.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class GitService {

    public String getLatestHash(Git git) {
        String command = "git ls-remote ";

        CommandLine commandLine = CommandLine.parse(command);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(1);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(pumpStreamHandler);
        try {
            executor.execute(commandLine);
        } catch (IOException e) {
            XxlJobLogger.log("[ERROR] {0}, could not get latest commit hash, git server isn't available.", git.getUrlBranch());
            XxlJobLogger.log(e);
            return "";
        }
        return outputStream.toString();
    }
}
