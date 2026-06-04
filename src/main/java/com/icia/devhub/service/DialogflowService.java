package com.icia.devhub.service;

import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.icia.devhub.config.DialogflowConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class DialogflowService {

    private static final Logger log = LoggerFactory.getLogger(DialogflowService.class);

    private final SessionsClient sessionsClient;
    private final String projectId;

    @Autowired
    public DialogflowService(SessionsClient sessionsClient, DialogflowConfig dialogflowConfig) {
        this.sessionsClient = sessionsClient;
        this.projectId = dialogflowConfig.getProjectId();
    }

    public String detectIntentTexts(String text) {
        String sessionId = UUID.randomUUID().toString();
        String responseText = "";

        try {
            String session = String.format("projects/%s/locations/global/agent/sessions/%s", projectId, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("ko");
            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentRequest request = DetectIntentRequest.newBuilder()
                    .setSession(session)
                    .setQueryInput(queryInput)
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(request);

            QueryResult queryResult = response.getQueryResult();
            responseText = queryResult.getFulfillmentText();

        } catch (Exception e) {
            log.error("Dialogflow 호출 실패", e);
            responseText = "죄송합니다. 일시적인 오류로 응답하지 못했습니다.";
        }

        return responseText;
    }
}
