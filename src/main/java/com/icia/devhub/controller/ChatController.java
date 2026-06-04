package com.icia.devhub.controller;

import com.icia.devhub.service.DialogflowService;
import com.icia.devhub.dto.event.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final DialogflowService dialogflowService;

    @Autowired
    public ChatController(DialogflowService dialogflowService) {
        this.dialogflowService = dialogflowService;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return dialogflowService.detectIntentTexts(message);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setType("JOIN");
        return chatMessage;
    }

}
