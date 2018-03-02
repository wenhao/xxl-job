package com.xxl.job.executor.service;

import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import org.apache.commons.exec.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.*;

@Service
public class GitService {

    private static final int GIT_HASH_SIZE = 40;
    private static final String EMPTY = "";

    public String getLatestHash(Git git) {
        String protocol = substringBefore(git.getUrl(), "://");
        String url = substringAfter(git.getUrl(), "://");
        String command = String.format("git ls-remote %s://%s:%s@%s %s", protocol, git.getUsername(), git.getPassword(), url, git.getBranch());

        CommandLine commandLine = CommandLine.parse(command);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(pumpStreamHandler);
        try {
            executor.execute(commandLine);
        } catch (IOException e) {
            XxlJobLogger.log("[ERROR] {0}, could not get latest commit hash, git server isn't available.", git.getUrlBranch());
            XxlJobLogger.log(e);
            return EMPTY;
        }
        String hash = substringBefore(outputStream.toString(), "\t");
        XxlJobLogger.log(hash);
        return (isNotBlank(hash) && hash.length() == GIT_HASH_SIZE) ? hash : EMPTY;
    }
}
