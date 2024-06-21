package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {



    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/")
    @RolesAllowed({"Manager", "Admin"})
    public ResponseEntity<ResponseWrapper> getProjects() {

        List<ProjectDTO> projectDTOList = projectService.listAllProjects();
        return ResponseEntity.ok(new ResponseWrapper("All projects retrieved", projectDTOList, HttpStatus.OK));

    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectCode") String projectCode ) {

        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Requested project retrieved", projectDTO, HttpStatus.OK));

    }

    @PostMapping("/")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO project) {

        projectService.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project Created", HttpStatus.CREATED));

    }

    @PutMapping
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO project) {

        projectService.update(project);
        return ResponseEntity.ok(new ResponseWrapper("Project updated",project, HttpStatus.OK));
    }

    @DeleteMapping("/{projectcode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String projectcode) {
        projectService.delete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project deleted", HttpStatus.OK));
    }

    @GetMapping("/manager/{userName}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(@PathVariable("userName") String userName) {
        List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManagerUserName(userName);
        return ResponseEntity.ok(new ResponseWrapper("Project deleted",projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/complete/{projectcode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper>  completeProject(@PathVariable("projectcode") String projectcode) {
        projectService.complete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project Completed", HttpStatus.OK)) ;
    }



    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {

        List<ProjectDTO> projects = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("List of projects retrieved", projects, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Requested project completed", HttpStatus.OK));
    }

}
