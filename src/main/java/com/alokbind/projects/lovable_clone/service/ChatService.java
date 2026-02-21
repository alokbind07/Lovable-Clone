package com.alokbind.projects.lovable_clone.service;

import com.alokbind.projects.lovable_clone.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getProjectChatHistory(Long projectId);
}
