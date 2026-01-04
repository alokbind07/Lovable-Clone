package com.alokbind.projects.lovable_clone.repository;

import com.alokbind.projects.lovable_clone.entity.ProjectMember;
import com.alokbind.projects.lovable_clone.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
}
