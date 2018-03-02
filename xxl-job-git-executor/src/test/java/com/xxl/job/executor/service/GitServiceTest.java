package com.xxl.job.executor.service;

import com.xxl.job.executor.domain.Git;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GitServiceTest {

    private GitService gitService;

    @Before
    public void setUp() {
        gitService = new GitService();
    }

    @Test
    public void should_get_latest_git_repository_hash() {
        // given
        Git git = new Git("https://github.com/wenhao/xxl-job.git,master,wenhao@126.com,1qaz!QAZ,pl000001");

        // when
        String latestHash = gitService.getLatestHash(git);

        //then
        assertThat(latestHash).isNotBlank();
    }
}