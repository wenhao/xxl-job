package com.xxl.job.executor.service;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import com.xxl.job.executor.entity.GitCommit;
import com.xxl.job.executor.repository.GitCommitRepository;
import com.xxl.job.executor.validator.ParameterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@JobHandler(value = "gitJobHandler")
public class GitJobHandler extends IJobHandler {

    private ParameterValidator parameterValidator;
    private GitCommitRepository gitCommitRepository;
    private GitService gitService;
    private DevOpsService devOpsService;

    @Autowired
    public GitJobHandler(ParameterValidator parameterValidator, GitCommitRepository gitCommitRepository,
                         GitService gitService, DevOpsService devOpsService) {
        this.parameterValidator = parameterValidator;
        this.gitCommitRepository = gitCommitRepository;
        this.gitService = gitService;
        this.devOpsService = devOpsService;
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("[INFO] Execute GitJobHandler with parameters {0}.", param);
        if (!parameterValidator.validate(param)) {
            XxlJobLogger.log("[ERROR] GitJobHandler parameters must have 5 parameters: git url, branch, username, password, pipeline number.");
            return FAIL;
        }

        Git git = new Git(param);
        String latestHash = gitService.getLatestHash(git);

        Optional<GitCommit> gitCommitOptional = gitCommitRepository.findByUrlBranch(git.getUrlBranch());
        if (!gitCommitOptional.isPresent()) {
            XxlJobLogger.log("[INFO] {0} latest commit hash not found, will trigger next time.", git.getUrlBranch());
            gitCommitRepository.save(new GitCommit(git.getUrlBranch(), latestHash));
            return SUCCESS;
        }

        GitCommit gitCommit = gitCommitOptional.get();
        if (gitCommit.getLatestHash().equals(latestHash)) {
            XxlJobLogger.log("[INFO] {0}, latest hash: {1}, no changes.", git.getUrlBranch(), latestHash);
            return SUCCESS;
        } else {
            boolean isPipelineRunning = devOpsService.isPipelineRunning(git.getPipeline());
            if (isPipelineRunning) {
                XxlJobLogger.log("[INFO] {0}, latest hash: {1}, changes detected, but pipeline {2} already running, will trigger next time.", git.getUrlBranch(), latestHash, git.getPipeline());
                return SUCCESS;
            }
            XxlJobLogger.log("[INFO] {0}, latest hash: {1}, changes detected, will trigger pipeline {2} now.", git.getUrlBranch(), latestHash, git.getPipeline());
            devOpsService.trigger(git.getPipeline());
            gitCommit.setLatestHash(latestHash);
            gitCommitRepository.save(gitCommit);
            return SUCCESS;
        }
    }
}