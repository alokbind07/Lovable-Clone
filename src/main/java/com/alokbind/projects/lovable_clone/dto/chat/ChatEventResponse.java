package com.alokbind.projects.lovable_clone.dto.chat;

import com.alokbind.projects.lovable_clone.entity.ChatMessage;
import com.alokbind.projects.lovable_clone.enums.ChatEventType;
import jakarta.persistence.*;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
