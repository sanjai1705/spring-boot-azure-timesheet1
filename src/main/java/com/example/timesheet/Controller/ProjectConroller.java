package com.example.timesheet.Controller;

import ch.qos.logback.core.net.server.Client;
import com.example.timesheet.Entity.*;
import com.example.timesheet.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins="https://timesheet-react.azurewebsites.net")
@RestController
@RequestMapping("/Timesheet")
public class ProjectConroller {
    @Autowired
    ProjectService projectService;

    @Autowired
    private ClientTableService clientTableService;


    @Autowired
    private ProjectEmployeeService projectEmployeeService;



    @Autowired
    private UsersService userService;

    @Autowired
    private EmployeeTimeentriesService employeeTimeentriesService;


    @GetMapping("/Project")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/Project/{projectId}")
    public Project getProjectById(@PathVariable Integer projectId) {

        return projectService.getProjectById(projectId);
    }
    @GetMapping("/Project/applicable")
    public List<Project> getProjectByapplicable(@RequestParam("applicable") boolean applicable) {

        return projectService.applicableDays(applicable);
    }

    @PostMapping("/Project/Create")
    public Project createProject(@RequestBody Project project) {

        return projectService.createProject(project);
    }

    @PutMapping("/Project/Update/{projectId}")
    public Project updateProject(@PathVariable Integer projectId, @RequestBody Project updatedProject) {
        return projectService.updateProject(projectId, updatedProject);
    }


    @DeleteMapping("/Project/Delete/{projectId}")
    public void deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
    }

    @GetMapping("/byClient/{client}")
    public ResponseEntity<List<Project>> getProjectsByClientId(@PathVariable ClientTable client) {
        List<Project> projects = projectService.getProjectsByClient(client);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }



}
