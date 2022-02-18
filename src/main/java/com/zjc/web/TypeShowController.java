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

import com.zjc.po.Type;
import com.zjc.service.BlogsService;
import com.zjc.service.TypeService;
import com.zjc.vo.BlogQuery;

/**
 * 
 * @author 周金城
 *
 */
@Controller
public class TypeShowController {
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private BlogsService blogService;
	
	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size =10,sort = {"updateTime"},
			direction =Sort.Direction.DESC)Pageable pageable,@PathVariable Long id,Model model) {
		
		List<Type> types=typeService.listTypeTop(10000);
		if(id==-1) {
			return "redirect:/ShowTypes";
		}
		BlogQuery blogQuery=new BlogQuery();
		blogQuery.setTypeId(id);
		blogQuery.setVisitor(true);
		model.addAttribute("types", types);
		model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
		model.addAttribute("activeTypeId", id);
		return "types";
	}
	
	@GetMapping("/ShowTypes")
	public String Showtypes(@PageableDefault(size =10,sort = {"updateTime"},
			direction =Sort.Direction.DESC)Pageable pageable,Model model) {
		
		List<Type> types=typeService.listTypeTop(10000);
		
		model.addAttribute("types", types);
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("activeTypeId", -1);
		return "types";
	}
}
