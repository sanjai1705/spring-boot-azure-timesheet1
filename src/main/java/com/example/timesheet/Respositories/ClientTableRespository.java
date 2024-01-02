package com.example.timesheet.Respositories;

import com.example.timesheet.Entity.ClientTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientTableRespository extends JpaRepository<ClientTable,Integer> {


}
