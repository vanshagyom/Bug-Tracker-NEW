package com.practice.OneYear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.practice.OneYear.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}