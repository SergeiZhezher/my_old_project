package com.example.libfilm.service;


import com.example.libfilm.additionalyMethods.AllMethods;
import com.example.libfilm.dao.Role;
import com.example.libfilm.dao.User;
import com.example.libfilm.dao.dto.CaptchaResponceDto;
import com.example.libfilm.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Watchable;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private Map<String, String> errors = null;
    private String captchaError = null;

    public String getCaptchaError() {
        return captchaError;
    }

    public void setCaptchaError(String captchaError) {
        this.captchaError = captchaError;
    }

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    UserRepo userRepo;
    @Autowired
    private MailSender mailSender;

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) userRepo.findByUsername(s);
    }

    public void addUser(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to LibFilm. Please confirm registration: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "activation code", message);
        }
        userRepo.save(user);

    }

    public boolean activateUser(String code) {
        System.out.println(code);
        User user = userRepo.findByActivationCode(code);
        if (user != null) {
            user.setActivationCode(null);
            user.setActive(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public void registration(User user, BindingResult bindingResult, String captchaResponce) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponceDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponceDto.class);


        if (bindingResult.hasErrors() || !response.isSuccess()) {
            setCaptchaError("confirm captcha");
            setErrors(AllMethods.getErrors(bindingResult));
        }
        else {
            String inpName = user.getUsername();
            System.out.println(inpName);
            String isAdmin = user.getUsername().replace('$', ' ').trim();
            if(userRepo.findByUsername(isAdmin) != null) {
                System.out.println("user: " + userRepo.findByUsername(user.getUsername()) + " существует" );
            }
            user = new User(isAdmin, user.getPassword(), false, UUID.randomUUID().toString(), user.getEmail());

            if (inpName.endsWith("$")) {
                user.setRoles(Collections.singleton(Role.ADMIN));
            } else {
                user.setRoles(Collections.singleton(Role.USER));
            }
            addUser(user);
        }

    }



}
