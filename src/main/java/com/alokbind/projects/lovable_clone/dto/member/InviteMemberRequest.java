package com.alokbind.projects.lovable_clone.dto.member;

import com.alokbind.projects.lovable_clone.enums.ProjectRole;

public record InviteMemberRequest(
        String email,
        ProjectRole role
) {
}
