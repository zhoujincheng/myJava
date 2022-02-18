package com.zjc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjc.po.Blog;
import com.zjc.vo.BlogQuery;

/**
 * 博客业务
 * @author 周金城
 *
 */
public interface BlogsService {
	
	Blog getBlog(Long id);
	
	Blog getBlogAndConvert(Long id);
	
	Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);
	
	Page<Blog> listBlogByUserId(Pageable pageable,Long userId);
	
	Page<Blog> listBlog(Pageable pageable);
	
	Page<Blog> listBlog(Long tagId,Pageable pageable);
	
	Page<Blog> listBlog(String query,Pageable pageable);
	
	//查询最多流览的分类
	List<Blog> ListRecommendBlogTop(Integer size);
	
	Map<String,List<Blog>> archiveBlog();
	
	Long countBlog();
	
	void blogViewsAdd(Long id);
	
	Blog saveBlog(Blog blog);
	
	Blog updateBlog(Long id,Blog blog);
	
	void deleteBlog(Long id);
}
