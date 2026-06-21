package com.alokbind.projects.lovable_clone.dto.chat;

import com.alokbind.projects.lovable_clone.entity.ChatEvent;
import com.alokbind.projects.lovable_clone.entity.ChatSession;
import com.alokbind.projects.lovable_clone.enums.MessageRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        String content,
        MessageRole role,   // USER, ASSISTANT
        List<ChatEventResponse>events,
        Integer tokensUsed,
        Instant createdAt
) {

}
