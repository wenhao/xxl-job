package com.xxl.job.executor.validator;

import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class ParameterValidator {

    private static final String SEPARATOR = ",";

    public boolean validate(String param) {
        return !isEmpty(param) && param.split(SEPARATOR).length == 3;
    }
}
