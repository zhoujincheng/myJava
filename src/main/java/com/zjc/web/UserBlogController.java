package com.zjc.web;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zjc.po.Blog;
import com.zjc.po.User;
import com.zjc.service.BlogsService;
import com.zjc.service.TagsService;
import com.zjc.service.TypeService;
import com.zjc.vo.BlogQuery;


/**
 * 博客页面控制器
 * @author 周金城
 *
 */
@Controller
public class UserBlogController {
	
	private static final String INPUT="blogsInput";
	private static final String LIST="blogs";
	private static final String REDIRECT_LIST="redirect:/blogs";
	
	@Autowired
	private BlogsService blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagsService tagsService;
	
	@GetMapping("/blogs")
	public String list(@PageableDefault(size =10,sort = {"updateTime"},
						direction =Sort.Direction.DESC)Pageable pageable,
						BlogQuery blogQuery,Model model,HttpSession session) {
		User user=(User) session.getAttribute("user");
		Long userId=user.getId();
		model.addAttribute("types",typeService.listType());
		model.addAttribute("page",blogService.listBlogByUserId(pageable, userId));
		return LIST;
	}
	
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size =10,sort = {"updateTime"},
						direction =Sort.Direction.DESC)Pageable pageable
						,BlogQuery blogQuery,Model model,HttpSession session) {
		User user=(User) session.getAttribute("user");
		Long userId=user.getId();
		model.addAttribute("page",blogService.listBlog(pageable, blogQuery));
		return "blogs :: blogList";
	}
	
	private void setTypeAndTag(Model model) {
		model.addAttribute("types",typeService.listType());
		model.addAttribute("tags",tagsService.ListTag());
	}
	
	@GetMapping("/blogs/input")
	public String input(Model model) {
		setTypeAndTag(model);
		model.addAttribute("blog", new Blog());
		return INPUT;
	}
	
	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {
		setTypeAndTag(model);
		Blog b=blogService.getBlog(id);
		b.init();
		model.addAttribute("blog", b);
		return INPUT;
	}
	
	@PostMapping("/blogs")
	public String post(Blog blog, RedirectAttributes attributes, HttpSession session,
			@RequestParam boolean published) {
		blog.setUser((User)session.getAttribute("user"));
				
			blog.setType(typeService.getType(blog.getType().getId()));
			blog.setTags(tagsService.ListTag(blog.getTagIds()));			
		
		
		Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
		
		if(b==null) {
			attributes.addFlashAttribute("message", "操作失败");
		}else {
			attributes.addFlashAttribute("message", "操作成功");
		}
		return REDIRECT_LIST;
	}
	
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "删除成功");
		return REDIRECT_LIST;
	}
}
