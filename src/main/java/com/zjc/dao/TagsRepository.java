package com.zjc.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zjc.po.Tag;

/**
 * 标签数据库操作层
 * @author 周金城
 *
 */
public interface TagsRepository extends JpaRepository<Tag, Long> {
	Tag findByName(String name);
	
	@Query("select t from Tag t")
	List<Tag> findTop(Pageable pageable);
}
