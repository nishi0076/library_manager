package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// SpringSecurityの設定を有効にする
@EnableWebSecurity

// 必ずWebSecurityConfigureAdapterを継承
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 認証認可に関する設定を追加
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		    .antMatchers("/loginForm").permitAll()
		    .anyRequest().authenticated();
		
		
		// ログインに関する設定
		http.formLogin()
		    .loginProcessingUrl("/login")
		    .loginPage("/loginForm")
		    .usernameParameter("email")
		    .passwordParameter("password")
		    .defaultSuccessUrl("/library", true)
		    .failureUrl("/loginForm?error");
		
		
		// ログアウトに関する設定
		http.logout()
		    .logoutUrl("/logout")
		    .logoutSuccessUrl("/loginForm");
	}
	
	
	// システム全体へのセキュリティ設定
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/css/**", "/js/**");
	}
	
	
	// パスワードのハッシュ化
	@Bean
	public PasswordEncoder passwoedEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
