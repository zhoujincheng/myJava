package com.zjc.service;

import java.util.List;

import com.zjc.po.Comment;

/**
 * 评论service接口
 * @author 周金城
 *
 */
public interface CommentService {
	
	List<Comment> listCommentByBlogId(Long blogId);
	
	Comment saveComment(Comment comment);
}
