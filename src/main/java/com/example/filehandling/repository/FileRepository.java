package com.example.filehandling.repository;

import com.example.filehandling.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Model, Long> {
}

