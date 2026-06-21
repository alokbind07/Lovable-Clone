package com.alokbind.projects.lovable_clone.dto.member;

import com.alokbind.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole role,
        Instant invitedAt
) {
}
