package com.calendar.calendar.Controller;

import com.calendar.calendar.Models.User;
import com.calendar.calendar.SecurityConfiguration;
import com.calendar.calendar.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfiguration securityConfiguration;

    @GetMapping("/all-users")
    @PreAuthorize("isAuthenticated()")
    public List<User> findAll() {
        return userService.findAll();
    }
    @PostMapping("/insert-user")
    @PreAuthorize("isAuthenticated()")
    public void insert(@RequestBody User user) {
        userService.insert(user);
    }
    @PutMapping("/save-user")
    @PreAuthorize("isAuthenticated()")
    public void save(@RequestBody User user) {
        userService.save(user);
    }
    @DeleteMapping("/delete-user")
    @PreAuthorize("isAuthenticated()")
    public void delete(@RequestBody User user) {
        userService.delete(user);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "Ви авторизувалися";
        } catch (AuthenticationException e) {
            return "Сталася помилка:  " + e.getMessage();
        }
    }
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(securityConfiguration.passwordEncoder().encode(user.getPassword()));
        userService.insert(user);
        return "Ви зареєструвалися";
    }
}
