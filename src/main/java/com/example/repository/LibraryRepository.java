package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
	
	// 必要に応じて機能を実装

}
