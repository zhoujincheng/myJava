package com.zjc.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zjc.po.Blog;
import com.zjc.vo.BlogQuery;

/**
 * 博客数据库操作层
 * @author 周金城
 *
 */
public interface BlogsRepository extends JpaRepository<Blog, Long>,
										 JpaSpecificationExecutor<Blog> {
	@Query("select b from Blog b where b.recommend = true")
	List<Blog> findTop(Pageable pageable);
	
	Page<Blog> PublishedTrue(Pageable pageable);
	
	Page<Blog> findByUserId(Pageable pageable,Long UserId);
	
		
	@Query("select b from Blog b where b.published = true")
	List<Blog> findToShow(Pageable pageable);
	
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> findByQuery(String query,Pageable pageable);
	
	@Query("select function('date_format',b.updateTime,'%Y') "
			+ "as year from Blog b group by function('date_format',b.updateTime,'%Y') "
			+ "order by year desc ")
	  List<String> findGroupYear();

	@Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
	    List<Blog> findByYear(String year);
}
