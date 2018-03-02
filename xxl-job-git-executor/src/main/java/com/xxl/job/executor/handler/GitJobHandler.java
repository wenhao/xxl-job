package com.xxl.job.executor.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.domain.Git;
import com.xxl.job.executor.service.DevOpsService;
import com.xxl.job.executor.validator.ParameterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JobHandler(value = "gitJobHandler")
public class GitJobHandler extends IJobHandler {

    private ParameterValidator parameterValidator;
    private DevOpsService devOpsService;

    @Autowired
    public GitJobHandler(ParameterValidator parameterValidator, DevOpsService devOpsService) {
        this.parameterValidator = parameterValidator;
        this.devOpsService = devOpsService;
    }

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("[INFO] Execute GitJobHandler with parameters {0}.", param);
        if (!parameterValidator.validate(param)) {
            XxlJobLogger.log("[ERROR] GitJobHandler parameters must have 5 parameters: git url, branch, username, password, pipeline number.");
            return FAIL;
        }
        return devOpsService.trigger(new Git(param)) ? SUCCESS : FAIL;
    }
}