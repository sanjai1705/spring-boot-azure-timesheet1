package com.example.timesheet.Respositories;

import com.example.timesheet.Entity.ClientTable;
import com.example.timesheet.Entity.EmployeeClientdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeClientdetailsRepository extends JpaRepository<EmployeeClientdetails,Integer> {

}
