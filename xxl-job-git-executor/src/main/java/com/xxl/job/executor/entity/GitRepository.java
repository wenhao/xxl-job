package com.xxl.job.executor.entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("git")
public class GitRepository implements Serializable {
    private String url;
    private String branch;
    private String commit;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
