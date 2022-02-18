package com.zjc.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.zjc.po.User;

/**
 * 关于我页面
 * @author 周金城
 *
 */
@Controller
public class AboutShowController {
	
	
	
	@GetMapping("/about")
	public String about(HttpSession session) {
		if(session.getAttribute("user") != null) {
			return "about";
		}else {
			return "/login";
		}
	}
	
	
}
