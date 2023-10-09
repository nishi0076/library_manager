package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
	
	// LIBRARY_IDとUSER_IDで対象を検索し、RENT_DATEが最新のレコードを取得(クエリメソッド)
	public Optional<Log> findFirstByLibraryIdAndUserIdOrderByRentDateDesc(Integer libraryId, Integer userId);
	
	// USER_IDで対象を検索して取得(クエリメソッド)
	public List<Log> findByUserId(Integer userId);
}
