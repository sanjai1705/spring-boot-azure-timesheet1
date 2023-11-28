package com.example.timesheet.Respositories;

import com.example.timesheet.Entity.StatusDetail;
import com.example.timesheet.Entity.StatusDetail1;
import com.example.timesheet.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatusDetail1Respository extends JpaRepository<StatusDetail1,Integer> {

    List<StatusDetail1> findByUserAndDate(Users user, Date date);



    StatusDetail1 findStatusDetailByUserAndDate(Users user, Date date);
}
