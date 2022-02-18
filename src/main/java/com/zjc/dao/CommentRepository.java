package com.zjc.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zjc.po.Comment;

/**
 * 评论功能持久层
 * @author 周金城
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long>{
	 List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
