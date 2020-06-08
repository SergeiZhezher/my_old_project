package com.example.libfilm;


import com.example.libfilm.dao.User;
import com.example.libfilm.repos.UserRepo;
import com.example.libfilm.service.MailSender;
import com.example.libfilm.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.Assert;
import org.springframework.validation.BindingResult;


@SpringBootTest
public class UnitTesting {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private MailSender mailSender;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        userService.addUser(user);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()); //ArgumentMatchers.contains("Welcome to LibFilm"));
    }
    @Test
    void duplicateUser() {
        BindingResult bindingResult = null;
        User user = new User();
        user.setUsername("test");
        Mockito.doReturn(new User()).when(userRepo).findByUsername("test");
        boolean b =  userService.registration(user, bindingResult, "6Le9u9sUAAAAAP4qoFkqsz8XcicStY0QYnGrxTLJ");
        Assert.assertFalse(b);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());

    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("testActivation");
        Mockito.doReturn(user).when(userRepo).findByActivationCode("activate");
        boolean activate = userService.activateUser("activate");
        Assert.assertNull(user.getActivationCode());
        Assert.assertTrue(user.getActive());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
}
