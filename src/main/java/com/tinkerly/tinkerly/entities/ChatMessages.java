package com.tinkerly.tinkerly.entities;

import com.tinkerly.tinkerly.payloads.ChatMessage;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class ChatMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;

    protected ChatMessages() {}

    public ChatMessages(ChatMessage chatMessage) {
        this.senderId = chatMessage.getSenderId();
        this.recipientId = chatMessage.getRecipientId();
        this.content = chatMessage.getContent();
        this.timestamp = chatMessage.getTimestamp();
    }
}

