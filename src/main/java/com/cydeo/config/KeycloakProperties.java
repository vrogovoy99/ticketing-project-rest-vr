package com.cydeo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KeycloakProperties {

//    @Value("${keycloak.realm}")
    @Value("${keycloak.realm}")
    private String realm;
//    @Value("${keycloak.auth-server-url}")
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
//    @Value("${keycloak.resource}")
    @Value("${keycloak.resource}")
    private String clientId;
//    @Value("${keycloak.credentials.secret}")
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

//    @Value("${master.user}")
    @Value("${master.user}")
    private String masterUser;
//    @Value("${master.user.password}")
    @Value("${master.user.password}")
    private String masterUserPswd;
//    @Value("${master.realm}")
    @Value("${master.realm}")
    private String masterRealm;
//    @Value("${master.client}")
    @Value("${master.client}")
    private String masterClient;

}
