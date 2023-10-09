package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.service.LibraryService;
import com.example.service.LogService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("/library")
public class LibraryController {
	
	private final LibraryService libraryService;
	private final LogService logService;
	
	// コンストラクタでインジェクション
	@Autowired
	public LibraryController(LibraryService libraryService, LogService logService) {
		this.libraryService = libraryService;
		this.logService = logService;
	}
	
    
	// データ一覧表示処理
	@GetMapping
	public String index(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Library> libraries = this.libraryService.findAll();
		model.addAttribute("libraries", libraries);
		model.addAttribute("user", loginUser.getUser());
		return "library/index";
	}
	
	
	// 書籍貸し出しフォームを表示する
	@GetMapping("/borrow")
	public String borrowingForm(@RequestParam("id") Integer id, Model model) {
		// viewから渡ってきた書籍idでデータ検索
		Library library = this.libraryService.findById(id);
		model.addAttribute("library", library);
		return "library/borrowingForm";
	}
	
	
	// 書籍貸し出し処理
	@PostMapping("/borrow")
	public String borrow(@RequestParam("id") Integer id, @RequestParam("return_due_date") String returnDueDate,
			             @AuthenticationPrincipal LoginUser loginUser) {
		this.libraryService.update(id, loginUser);
		this.logService.update(id, returnDueDate, loginUser);
		return "redirect:/library";
	}
	
	
	// 書籍返却処理
	@PostMapping("/return")
	public String returnBook(@RequestParam("id") Integer id, @AuthenticationPrincipal LoginUser loginUser) {
		this.libraryService.returnUpdate(id);
		this.logService.returnUpdate(id, loginUser);
		return "redirect:/library";
	}
	
	
	// 貸し出し履歴の表示処理
	@GetMapping("/history")
	public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Log> logs = this.logService.findByuserId(loginUser.getUser().getId());
		model.addAttribute("logs", logs);
		return "library/borrowHistory";
	}
}
