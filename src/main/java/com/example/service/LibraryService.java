package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

	
	// データ1件取得用メソッド
	public Library findById(Integer id) {
		Optional<Library> optionalLibrary = this.libraryRepository.findById(id);
		Library library = optionalLibrary.get();
		return library;
	}
	
	
	// 書籍貸し出し用メソッド
	public Library update(Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		Library library = this.findById(id);
		
		// カラムに値をセット
		library.setUserId(loginUser.getUser().getId());
		
		// 更新
		return this.libraryRepository.save(library);
	}
	
	
	// 書籍返却用メソッド
	public Library returnUpdate(Integer id) {
		Library library = this.findById(id);
		
		// userIdを0(貸し出ししていない状態)にリセット
		library.setUserId(0);
		
		// 更新
		return this.libraryRepository.save(library);
	}
}
