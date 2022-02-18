package com.zjc.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjc.NotFoundException;
import com.zjc.po.Blog;
import com.zjc.service.BlogsService;
import com.zjc.service.TagsService;
import com.zjc.service.TypeService;
import com.zjc.vo.BlogQuery;

/**
 * 
 * @author 周金城
 *页面控制器
 */


@Controller
public class IndexController {
	
	@Autowired
	private BlogsService blogService;	
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagsService tagsService;
    
	@GetMapping("/")
	public String index(@PageableDefault(size =10,sort = {"createTime"},
			direction =Sort.Direction.DESC)Pageable pageable,Model model) {
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagsService.ListTagTop(10));
		model.addAttribute("recommendBlogs", blogService.ListRecommendBlogTop(8));
		return "index";
	}
	
	@PostMapping("/search")
	public String search(@PageableDefault(size =10,sort = {"updateTime"},
			direction =Sort.Direction.DESC)Pageable pageable,
			@RequestParam String query,Model model) {
		
		model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
		model.addAttribute("query",query);
		return "search";
	}
	
	@GetMapping("/blog/{id}")
	public String blog(@PathVariable Long id,Model model) {
		blogService.blogViewsAdd(id);
		model.addAttribute("blog",blogService.getBlogAndConvert(id));
		 return "blog";
	}
	
	 @GetMapping("/footer/newblog")
	    public String newblogs(Model model) {
	        model.addAttribute("newblogs", blogService.ListRecommendBlogTop(3));
	        return "_fragments :: newblogList";
	    }
	
}
