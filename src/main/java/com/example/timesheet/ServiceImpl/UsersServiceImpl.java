package com.example.timesheet.ServiceImpl;

import com.example.timesheet.Entity.Users;
import com.example.timesheet.Respositories.UsersRespository;
import com.example.timesheet.Service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRespository usersRespository;



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



   /* @Override
    public void sendPasswordResetEmail(String email) {
        // Implement logic to send an email with a password reset link
        // You may generate a unique token, save it in the database, and include it in the email link
        // Here, I'm just demonstrating the logic, not the actual email sending mechanism
        Users user = usersRespository.findByEmail(email);
        if (user != null) {
            // Generate and send reset link with a unique token
            String resetToken = generateUniqueToken();
            user.setResetToken(resetToken);
            usersRespository.save(user);
            // Send email with the reset link using your email sending logic
        }
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Users user = usersRespository.findByEmailAndResetToken(email, newPassword);
        if (user != null) {
            // Reset password and clear the reset token
            user.setPassword(newPassword);
            user.setResetToken(null);
            usersRespository.save(user);
        } else {
            throw new RuntimeException("Invalid reset token or email");
        }
    }

    private String generateUniqueToken() {
        // Implement logic to generate a unique token (e.g., UUID)
        return UUID.randomUUID().toString();
    }*/










}
