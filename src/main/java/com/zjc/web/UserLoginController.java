package com.zjc.web;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zjc.po.User;
import com.zjc.service.BlogsService;
import com.zjc.service.UserService;

/**
 * 登录控制器
 * @author 周金城
 *
 */
@Controller
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String login(@RequestParam String username,@RequestParam String password,
						HttpSession session,Model model) {
		User user=userService.checkUser(username, password);
		if(user!=null) {
			user.setPassword(null);//不让前端拿到密码
			session.setAttribute("user", user);
			return "longIndex";
		}else {
			model.addAttribute("message", "用户名或密码错误");
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
}
