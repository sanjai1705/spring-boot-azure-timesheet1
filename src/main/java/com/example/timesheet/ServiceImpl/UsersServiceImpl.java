package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.Users;
import com.example.timesheet.Respositories.UsersRespository;
import com.example.timesheet.Service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRespository usersRespository;

    @Autowired
    private JavaMailSender javaMailSender; // Autowire JavaMailSender

    @Value("${spring.mail.username}") // Assuming you have configured email properties in your application.properties
    private String fromEmail;



    @Override
    public List<Users> getAllUsers() {
        return usersRespository.findAll();
    }

    @Override
    public Users getUserById(Integer userId) {

        return usersRespository.findById(userId).orElse(null);
    }

    @Override
    public Users createUser(Users user) {
        if (usersRespository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (usersRespository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }
        return usersRespository.save(user);
    }

    @Override
    public Users updateUser(Integer userId, Users user) {
        if (usersRespository.existsById(userId)) {
            user.setUserId(userId);
            return usersRespository.save(user);
        }
        return null; // Handle not found case
    }

    @Override
    public void deleteUser(Integer userId) {
        usersRespository.deleteById(userId);
    }
    @Override
    public Users validateUser(String username, String password) {
        try {
            return usersRespository.findByUsernameAndPassword(username, password);
        } catch (NoResultException ex) {
            // Handle the case when no user is found.
            return null;
        }
    }

    @Override
    public List<Users> getUsersByRoleId(Integer roleId) {

        return usersRespository.findByRoleRoleID(roleId);
    }


    private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;
   @Override
    public void sendPasswordResetEmail(String email) {

        Users user = usersRespository.findByEmail(email);
        if (user != null) {


            // Generate and send reset link with a unique token
            String resetToken = generateUniqueToken();
            user.setResetToken(resetToken);
            usersRespository.save(user);

        }
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Users user = usersRespository.findByEmailAndResetToken(email, newPassword);
        if (user != null && isTokenValid(user.getResetToken())){
            // Check if the token is still valid
            user.setPassword(newPassword);
            user.setResetToken(null);
            usersRespository.save(user);
        } else {
            throw new RuntimeException("Invalid reset token or email");
        }
    }

    private String generateUniqueToken() {
        long expirationTime = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME;
        return UUID.randomUUID().toString() + "|" + expirationTime;
    }

    private boolean isTokenValid(String token) {
        // Extract expiration time from the token and check if it's still valid
        String[] tokenParts = token.split("\\|");
        if (tokenParts.length == 2) {
            long expirationTime = Long.parseLong(tokenParts[1]);
            return expirationTime >= System.currentTimeMillis();
        }
        return false;
    }

    private void sendResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText("Click the following link to reset your password: http://your-frontend-url/reset?token=" + resetToken);

        // Send the email
        javaMailSender.send(message);
    }
















}
