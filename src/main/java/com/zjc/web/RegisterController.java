package com.zjc.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zjc.service.UserService;

/**
 * 注册页面控制器
 * @author 周金城
 *
 */
@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/toRegister")
	public String toRegister() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam String username,@RequestParam String nickname,
						@RequestParam String password,@RequestParam String email,							
							HttpSession session,Model model) {
		if(userService.checkEmail(email)) {
			userService.addUser(username,nickname, password, email);		
			return "login";
		}else {	
			model.addAttribute("message", "该邮箱已注册");			
			return "register";
		}
	}
}
