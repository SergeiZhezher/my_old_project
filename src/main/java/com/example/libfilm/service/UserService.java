package com.example.libfilm.service;


import com.example.libfilm.dao.Role;
import com.example.libfilm.dao.User;
import com.example.libfilm.repos.UserRepo;
import com.example.libfilm.utils.RegistrationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private RegistrationHandler registrationHandler = new RegistrationHandler();
    private User user;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (UserDetails) userRepo.findByUsername(s);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user != null) {
            user.setActivationCode(null);
            user.setActive(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public boolean registration(User u, BindingResult bindingResult, String captchaResponce) {

        user = u;
        String name = user.getUsername().replace('$', ' ').trim();

        if (bindingResult == null || userRepo.findByUsername(name) != null) {
            registrationHandler.failedRegistration(bindingResult, captchaResponce);

            return false;
        }
        else {

            giveOutRole( user.getUsername());
            user.setActive(false);
            user.setActivationCode( UUID.randomUUID().toString() );

            sendCodeConfirmation();

            userRepo.save(user);
        }
        return true;
    }

    private void giveOutRole(String name) {
        if (name.endsWith("$")) {
            user.setRoles(Collections.singleton(Role.ADMIN));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }
    }

    private void sendCodeConfirmation() {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to LibFilm. Please confirm registration: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "activation code", message);
        }
    }

}
