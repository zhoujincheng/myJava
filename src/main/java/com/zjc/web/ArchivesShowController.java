package com.zjc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zjc.service.BlogsService;

/**
 * 归档页面控制器
 * @author 周金城
 *
 */
@Controller
public class ArchivesShowController {
	
	@Autowired
	private BlogsService blogService;
	
	@GetMapping("/archives")
	public String archives(Model model) {
		model.addAttribute("archiveMap", blogService.archiveBlog());
		model.addAttribute("blogCount", blogService.countBlog());
		return "archives";
	}
}
