package com.xxl.job.executor.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("git")
public class GitCommit implements Serializable {

    @Id
    private String urlBranch;
    private String latestHash;

    public GitCommit(String urlBranch, String latestHash) {
        this.urlBranch = urlBranch;
        this.latestHash = latestHash;
    }

    public String getUrlBranch() {
        return urlBranch;
    }

    public void setUrlBranch(String urlBranch) {
        this.urlBranch = urlBranch;
    }

    public String getLatestHash() {
        return latestHash;
    }

    public void setLatestHash(String latestHash) {
        this.latestHash = latestHash;
    }
}
