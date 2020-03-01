package com.example.libfilm.dao.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponceDto {
        private boolean success;
        @JsonAlias("error-codes")
        private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCode(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
