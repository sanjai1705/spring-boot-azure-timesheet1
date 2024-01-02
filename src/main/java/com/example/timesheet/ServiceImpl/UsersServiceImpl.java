package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.Users;
import com.example.timesheet.Respositories.UsersRespository;
import com.example.timesheet.Service.UsersService;
import jakarta.persistence.NoResultException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);
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
            Users user = usersRespository.findByUsername(username);

            // Check if the user exists
            if (user == null) {
                return null;
            }

            // Use BCryptPasswordEncoder to check if the provided password matches the stored hashed password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Passwords match
                return user;
            } else {
                // Passwords do not match
                return null;
            }
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
    public boolean sendPasswordResetEmail(String email) {
        Users user = usersRespository.findByEmail(email);

        if (user != null) {
            // Generate and send reset link with a unique token
            String resetToken = generateUniqueToken();
            user.setResetToken(resetToken);

            Date resetTokenExpiry = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
            user.setResetTokenExpiry(resetTokenExpiry);
            usersRespository.save(user);
            sendResetEmail(email, user.getResetToken());
            return true; // Email sent successfully
        } else {
            // User not found
            return false; // Email not sent
        }
    }



    private String generateUniqueToken() {
        //long expirationTime = System.currentTimeMillis() + TOKEN_EXPIRATION_TIME;
        return UUID.randomUUID().toString() ;//+ "|" + expirationTime;
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



    @Override
    public boolean resetPassword(String resetToken, String email, String newPassword) {
        // Find user by reset token and email
        Users user = usersRespository.findByResetTokenAndEmail(resetToken, email);

        // Check if user, reset token, and email are valid
        if (user != null && user.getResetToken() != null &&
                user.getResetToken().equals(resetToken) && user.getEmail().equals(email)&&
                user.getResetTokenExpiry() != null && user.getResetTokenExpiry().after(new Date())) {
            // Reset password if reset token and email are valid
            String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
            user.setPassword(encryptedPassword);
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            usersRespository.save(user);

            return true; // Password reset successfully
        } else {
            return false; // Invalid reset token, email, or user not found
        }
    }




}
