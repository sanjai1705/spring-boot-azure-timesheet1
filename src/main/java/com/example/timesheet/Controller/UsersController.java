package com.example.timesheet.Controller;

import com.example.timesheet.Entity.Users;
import com.example.timesheet.Service.UsersService;

import com.example.timesheet.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="https://timesheet-react.azurewebsites.net")
@RequestMapping("/Timesheet")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/Users")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/Users/{userId}")
    public Users getUserById(@PathVariable Integer userId) {

        return usersService.getUserById(userId);
    }
    @GetMapping("/usersByRoleId")
    public List<Integer> getUsersByRoleId(@RequestParam Integer roleId) {
        List<Users> users = usersService.getUsersByRoleId(roleId);

        return users.stream().map(Users::getUserId).collect(Collectors.toList());
    }




    @PostMapping("/Users/Create")
    public Users createUser(@RequestBody Users user) {

        return usersService.createUser(user);
    }

    @PutMapping("/Users/Update/{userId}")
    public Users updateUser(@PathVariable Integer userId, @RequestBody Users user) {
        return usersService.updateUser(userId, user);
    }

    @DeleteMapping("/Users/Delete/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        usersService.deleteUser(userId);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Users users) {
        Users user = usersService.validateUser(users.getUsername(),users.getPassword());

        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userid", user.getUserId());
            response.put("Rolename", user.getRole().getRoleName());

            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendPasswordResetEmail(@RequestParam String email) {
        boolean emailSent = usersService.sendPasswordResetEmail(email);

        if (emailSent) {
            return ResponseEntity.ok("Password reset email sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with email " + email + " not found");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String resetToken,
            @RequestParam String email,
            @RequestParam String newPassword) {

        boolean Successful = usersService.resetPassword(resetToken, email, newPassword);

        if (Successful) {
            return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid reset token, email, or user not found", HttpStatus.BAD_REQUEST);
        }
    }

    }
