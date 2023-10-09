package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class LoginUserService implements UserDetailsService {

	private final UserRepository userRepository;
	
	// インジェクション
	@Autowired
	public LoginUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	
	public LoginUser loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// emailからユーザーを取得
		User user = this.userRepository.findByEmail(email);
		// ユーザーが見つからない場合例外を発生させる
		if (user == null) {
			throw new UsernameNotFoundException("ユーザーが見つかりません");
		}
		// ユーザーが見つかった場合はUserDetailsを返却
		return new LoginUser(user);
	}
}
