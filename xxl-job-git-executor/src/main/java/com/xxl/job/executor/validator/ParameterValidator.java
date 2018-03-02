package com.xxl.job.executor.validator;

import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class ParameterValidator {

    private static final String SEPARATOR = ",";

    public boolean validate(String param) {
        return isNotBlank(param) && param.split(SEPARATOR).length == 5;
    }
}
