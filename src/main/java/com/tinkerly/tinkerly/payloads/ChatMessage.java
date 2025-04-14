package com.tinkerly.tinkerly.payloads;

import com.tinkerly.tinkerly.entities.ChatMessages;
import com.tinkerly.tinkerly.types.MessageType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String id;
    private MessageType type;
    private String content;
    private String senderId;
    private String recipientId;
    private Date timestamp;

    public ChatMessage(ChatMessages chatMessage) {
        this.id = chatMessage.getId();
        this.content = chatMessage.getContent();
        this.senderId = chatMessage.getSenderId();
        this.recipientId = chatMessage.getRecipientId();
        this.timestamp = chatMessage.getTimestamp();

    }
}
