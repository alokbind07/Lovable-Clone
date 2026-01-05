package com.alokbind.projects.lovable_clone.service;

import com.alokbind.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.alokbind.projects.lovable_clone.dto.member.MemberResponse;
import com.alokbind.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.alokbind.projects.lovable_clone.entity.ProjectMember;

import java.util.List;

public interface ProjectMemberService {

    List<MemberResponse> getProjectMembers(Long projectId, Long userId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userid);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);

    void removeProjectMember(Long projectId, Long memberId, Long userId);
}
