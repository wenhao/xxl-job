package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import com.xxl.job.executor.validator.ParameterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "gitJobHandler")
@Component
public class GitJobHandler extends IJobHandler {

    @Autowired
    private ParameterValidator parameterValidator;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("[INFO] Execute GitJobHandler with parameters {0}.", param);
        if (!parameterValidator.validate(param)) {
            XxlJobLogger.log("[ERROR] GitJobHandler parameters must have 4 parameters: git url, branch, username and password.");
            return FAIL;
        }
        Git git = new Git(param);
        return SUCCESS;
    }
}
