package com.zjc.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.zjc.po.Comment;
import com.zjc.po.User;
import com.zjc.service.BlogsService;
import com.zjc.service.CommentService;

/**
 * 评论控制器
 * @author 周金城
 *
 */
@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogsService blogService;
	
	@Value("${comment.avatar}")
	private String avatar;
	
	
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId,Model model) {
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}
	
	@PostMapping("/comments")
	public String post(Comment comment,HttpSession session) {
		Long blogId=comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		User user=(User) session.getAttribute("user");
		if(user!=null) {
			comment.setAvatar(user.getAvatar());
			comment.setAminComment(true);
			
		}else {
			comment.setAvatar(avatar);					
		}
		commentService.saveComment(comment);		
		return "redirect:/comments/"+ blogId;
	}
}
