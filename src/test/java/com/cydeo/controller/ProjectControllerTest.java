package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static UserDTO userDTO;
    static ProjectDTO projectDTO;
    static String token;

    @BeforeAll
    static void setUp(){
        // get token from postman
//        token="Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxOVdUWUhfVW9ETkZSN1g3ZWFsNkYzSGtmWUNlWWN4d3RIcmZoTU1LYjdNIn0.eyJleHAiOjE3MjA2MzY4MTYsImlhdCI6MTcyMDYzNjUxNiwianRpIjoiNDE0MWYwMDEtM2JhZC00Yzc3LTk1ZjQtOTJlMDQ2YmNiNTMzIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL2N5ZGVvLWRldiIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJlNDM0NTQ3YS1lZjI5LTQyMjktOWU1Ny1iMmY3NTljZDhjNWMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ0aWNrZXRpbmctYXBwIiwic2Vzc2lvbl9zdGF0ZSI6ImEyNDUzZTc4LTJiOTQtNDE4My05NDcyLTFjZGQyNDc4MDZjZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDo4MDgxIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1jeWRlby1kZXYiXX0sInJlc291cmNlX2FjY2VzcyI6eyJ0aWNrZXRpbmctYXBwIjp7InJvbGVzIjpbIk1hbmFnZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJzaWQiOiJhMjQ1M2U3OC0yYjk0LTQxODMtOTQ3Mi0xY2RkMjQ3ODA2Y2YiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Im1pa2UxIG1pa2UxIiwicHJlZmVycmVkX3VzZXJuYW1lIjoibWlrZTEiLCJnaXZlbl9uYW1lIjoibWlrZTEiLCJmYW1pbHlfbmFtZSI6Im1pa2UxIiwiZW1haWwiOiJtaWtlMSJ9.cWGYfdQ93M3lanPEORJTnggQOvM-Ug2t08h-xlp1Yc-fkcFJ8R6oGp7Q4AQptT4iGzrlhh5WhclGS54cFZRSQnegB119Fg35AK609dFO9U3uvn7g-P9LHCuedFPsZEXW5xIj9LPLmJJECe3vvXP3kdGKEOPM1qTzGbv50e9Z1AJza5JEJsVEesfIJnb6VBKCWDmUAat-Wp80FIaP3ZTN5vpHaTUaxRxEnkKbLoXTuz2Z4efQfUhL6boV2PkIdQFU688LRYhZzuT9E9-PuqClzHcTG27TBQoYvgLT4vVkqs1eg2_36yGx_RiMpNDakbxUQbgfOd8NCNJiW9ppl7gBsQ";
        token="Bearer " + makeRequest();

        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("ozzy")
                .lastName("ozzy")
                .userName("ozzy")
                .passWord("Abc1")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("Api1")
                .projectName("Api-ozzy")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("Api Test")
                .projectStatus(Status.OPEN)
                .build();
    }

    @Test
    public void givenNoToken_whenGetRequest() throws Exception {

        //without passing a sec token security will fail - code 400
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void givenToken_whenGetRequest() throws Exception {

        //without passing a sec token security will fail - code 400
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/project")
                .header("Authorization", token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager.userName").isNotEmpty());

//        MvcResult result = mvc.perform(MockMvcRequestBuilders
//                .get("/api/v1/project")
//                .header("Authorization", token)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        System.out.println(result.toString());
    }

    @Test
    public void givenToken_createProject() throws Exception {
        projectDTO.setProjectCode("SP15");

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenToken_updateProject() throws Exception{
        projectDTO.setProjectName("Updated project name");

        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void givenToken_deleteProject() throws Exception{

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/project/" + projectDTO.getProjectCode())
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String makeRequest() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", "ticketing-app");
        map.add("client_secret", "6sOJLsuimxvZvvwgrCBCgcDCEY9cVYno");
        map.add("username", "mike1");
        map.add("password", "Abc1");
        map.add("scope", "openid");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        System.out.println("DEBUG: pre Keycloak call");

        ResponseEntity<ResponseDTO> response =
                restTemplate.exchange("http://localhost:8080/auth/realms/cydeo-dev/protocol/openid-connect/token",
                        HttpMethod.POST,
                        entity,
                        ResponseDTO.class);

        System.out.println("DEBUG: post Keycloak call");

        if (response.getBody() != null) {
            return response.getBody().getAccess_token();
        }

        return "";

    }
}