package com.example.libfilm.utils;


import com.example.libfilm.dao.dto.CaptchaResponceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

public class RegistrationHandler {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private String captchaError = null;
    private Map<String, String> errors = null;

    public void failedRegistration(BindingResult bindingResult, String captchaResponce) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponceDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponceDto.class);

        if (bindingResult.hasErrors() || !response.isSuccess()) {
            setCaptchaError("confirm captcha");
            setErrors(BetaFunctions.getErrors(bindingResult));
        }

    }

    public void errorHandlingAndVerification(Model model) {
        if (getErrors() != null || getCaptchaError() != null) {
            model.addAttribute("captchaError", getCaptchaError());
            model.mergeAttributes(getErrors());
        }
        setErrors(null);
        setCaptchaError(null);
    }

    private Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getCaptchaError() {
        return captchaError;
    }

    public void setCaptchaError(String captchaError) {
        this.captchaError = captchaError;
    }
}
