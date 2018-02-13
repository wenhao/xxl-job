package com.xxl.job.executor.domain;

public class Git {
    private String url;
    private String username;
    private String password;

    public Git(String param) {
        String[] params = param.split(",");
        this.url = params[0];
        this.username = params[1];
        this.password = params[2];
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
