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


    @GetMapping("/client/{clientId}/project-employee-timeentries")
    public ResponseEntity<Object> getProjectEmployeeTimeentriesForClient(
            @PathVariable Integer clientId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        try {
            List<Project> projects = projectService.getProjectsByClientId(clientId);
            List<ProjectEmployee> projectEmployees = new ArrayList<>();
            List<EmployeeTimeentries> employeeTimeentries = new ArrayList<>();

            for (Project project : projects) {
                List<ProjectEmployee> projectEmployeeList = projectService.getProjectEmployeesByProjectId(project.getProjectId());
                projectEmployees.addAll(projectEmployeeList);

                for (ProjectEmployee projectEmployee : projectEmployeeList) {
                    List<EmployeeTimeentries> timeEntries = projectService.getEmployeeTimeentriesByEmpIdAndDateRange(projectEmployee.getEmpID(), startDate, endDate);
                    employeeTimeentries.addAll(timeEntries);
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("projects", projects);
            result.put("projectEmployees", projectEmployees);
            result.put("employeeTimeentries", employeeTimeentries);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
