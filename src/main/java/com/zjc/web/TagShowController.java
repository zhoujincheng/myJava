package com.zjc.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zjc.po.Tag;
import com.zjc.service.BlogsService;
import com.zjc.service.TagsService;

import com.zjc.vo.BlogQuery;

/**
 * 
 * @author 周金城
 *
 */
@Controller
public class TagShowController {
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private BlogsService blogService;
	
	@GetMapping("/tags/{id}")
	public String tags(@PageableDefault(size =10,sort = {"updateTime"},
			direction =Sort.Direction.DESC)Pageable pageable,@PathVariable Long id,Model model) {
		
		List<Tag> tags=tagsService.ListTagTop(10000);
		if(id==-1) {
			return "redirect:/ShowTags";
		}
		
		model.addAttribute("tags", tags);
		model.addAttribute("page", blogService.listBlog(id,pageable));
		model.addAttribute("activeTagId", id);
		return "tags";
	}
	
	@GetMapping("/ShowTags")
	public String Showtypes(@PageableDefault(size =10,sort = {"updateTime"},
			direction =Sort.Direction.DESC)Pageable pageable,Model model) {
		
		List<Tag> tags=tagsService.ListTagTop(10000);
		
		model.addAttribute("tags", tags);
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("activeTagId", -1);
		return "tags";
	}
}
