package com.xxl.job.executor.service;

import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import com.xxl.job.executor.entity.GitCommit;
import com.xxl.job.executor.repository.GitCommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class DevOpsService {

    private GitService gitService;
    private GitCommitRepository gitCommitRepository;
    private PipelineService pipelineService;

    @Autowired
    public DevOpsService(GitService gitService, GitCommitRepository gitCommitRepository, PipelineService pipelineService) {
        this.gitService = gitService;
        this.gitCommitRepository = gitCommitRepository;
        this.pipelineService = pipelineService;
    }

    public boolean trigger(Git git) {
        String latestHash = gitService.getLatestHash(git);
        if (isEmpty(latestHash)) {
            XxlJobLogger.log("[ERROR] {0}, could not get latest commit hash, git server isn't available.", git.getUrlBranch());
            return false;
        }

        GitCommit savedGitCommit = gitCommitRepository.findOne(git.getUrlBranch());
        if (isNull(savedGitCommit) || isEmpty(savedGitCommit.getLatestHash())) {
            XxlJobLogger.log("[INFO] {0} latest commit hash not found, will trigger next time.", git.getUrlBranch());
            gitCommitRepository.save(new GitCommit(git.getUrlBranch(), latestHash));
            return true;
        }

        if (latestHash.equals(savedGitCommit.getLatestHash())) {
            XxlJobLogger.log("[INFO] {0}, latest hash: {1}, no changes.", git.getUrlBranch(), latestHash);
            return true;
        }

        if (pipelineService.isPipelineRunning(git.getPipeline())) {
            XxlJobLogger.log("[INFO] {0}, latest hash: {1}, changes detected, but pipeline {2} already running, will trigger next time.", git.getUrlBranch(), latestHash, git.getPipeline());
            return true;
        }

        XxlJobLogger.log("[INFO] {0}, latest hash: {1}, changes detected, will trigger pipeline {2} now.", git.getUrlBranch(), latestHash, git.getPipeline());
        savedGitCommit.setLatestHash(latestHash);
        gitCommitRepository.save(savedGitCommit);
        return pipelineService.runPipeline(git.getPipeline());
    }
}
