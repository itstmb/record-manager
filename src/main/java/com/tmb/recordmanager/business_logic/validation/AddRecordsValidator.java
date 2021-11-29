package com.tmb.recordmanager.business_logic.validation;

import org.springframework.stereotype.Component;

@Component
public class AddRecordsValidator implements BasicValidator {

    public static final String validatorName = "addRecordsValidator";

    @Override
    public boolean validate(Object var1) {
        return false;
    }
}
