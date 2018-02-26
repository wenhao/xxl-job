package com.xxl.job.executor.domain;

import java.net.URLEncoder;

public class Git {
    private final String url;
    private final String branch;
    private final String username;
    private final String password;

    public Git(String param) {
        String[] params = param.split(",");
        this.url = params[0].trim();
        this.branch = params[1].trim();
        this.username = params[2].trim();
        this.password = URLEncoder.encode(params[3].trim());
    }

    public String getUrl() {
        return url;
    }

    public String getBranch() {
        return branch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
