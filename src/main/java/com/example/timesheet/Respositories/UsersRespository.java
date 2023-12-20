package com.example.timesheet.Respositories;

import com.example.timesheet.Entity.Roles;
import com.example.timesheet.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRespository extends JpaRepository<Users,Integer> {

    Users findByUsernameAndPassword(String username, String password);

    List<Users> findByRoleRoleID(Integer roleId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    Users findByEmail(String email);

    Users findByEmailAndResetToken(String email, String newpassword);


}
