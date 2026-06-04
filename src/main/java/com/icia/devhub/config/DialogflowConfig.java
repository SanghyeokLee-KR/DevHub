package com.icia.devhub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class DialogflowConfig {

    @Getter
    @Value("${dialogflow.project-id}")
    private String projectId;

    @Value("${dialogflow.credentials.path}")
    private Resource credentialsPath;

    @Bean
    public SessionsClient sessionsClient() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsPath.getInputStream())
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
        SessionsSettings sessionsSettings = SessionsSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
        return SessionsClient.create(sessionsSettings);
    }
}
