package com.icia.devhub.dto.event;

import lombok.Data;

@Data
public class ChatMessage {
    private String type;        // 메시지 유형 ("JOIN", "CHAT" 등)
    private String content;
    private String profile;
    private String sender;
}
