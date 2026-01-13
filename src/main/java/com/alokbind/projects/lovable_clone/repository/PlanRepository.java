package com.alokbind.projects.lovable_clone.repository;

import com.alokbind.projects.lovable_clone.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
