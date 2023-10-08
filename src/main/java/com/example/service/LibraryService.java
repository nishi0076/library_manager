package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Library;
import com.example.repository.LibraryRepository;

@Service
public class LibraryService {

	private final LibraryRepository libraryRepository;
	
	// コンストラクタでインジェクション
	@Autowired
	public LibraryService(LibraryRepository libraryRepository) {
		this.libraryRepository = libraryRepository;
	}
	
	
	// データ全件取得用メソッド
	public List<Library> findAll() {
		return this.libraryRepository.findAll();
	}
}
