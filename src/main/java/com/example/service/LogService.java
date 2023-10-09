package com.example.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.example.entity.Log;
import com.example.repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository;
	
	@Autowired
	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}
	
	
	// 書籍貸し出し用メソッド
	public Log update(Integer id, String returnDueDate,
			           @AuthenticationPrincipal LoginUser loginUser) {
		Log log = new Log();
		
		// それぞれのカラムに値をセット
		log.setLibraryId(id);
		log.setUserId(loginUser.getUser().getId());
		log.setRentDate(LocalDateTime.now());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		log.setReturnDueDate(LocalDateTime.parse(returnDueDate + " 00:00:00", formatter));
		log.setReturnDate(null);
		
		// 更新
		return this.logRepository.save(log);
	}
	
	
	// 書籍返却用メソッド
	public Log returnUpdate(Integer libraryId, @AuthenticationPrincipal LoginUser loginUser) {
		Optional<Log> optionalLog = 
		this.logRepository.findFirstByLibraryIdAndUserIdOrderByRentDateDesc(libraryId, loginUser.getUser().getId());
		Log log = optionalLog.get();
		
		// retunDateカラムに値をセット
		log.setReturnDate(LocalDateTime.now());
		
		// 更新
		return this.logRepository.save(log);
	}
	
	
	// 貸し出し履歴表示用メソッド
	public List<Log> findByuserId(Integer userId) {
		return this.logRepository.findByUserId(userId);
	}
}
