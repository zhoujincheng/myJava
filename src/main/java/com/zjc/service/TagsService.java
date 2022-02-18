package com.zjc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjc.po.Tag;

/**
 * 标签业务接口
 * @author 周金城
 *
 */
public interface TagsService {
	
	//新增
	Tag saveTag(Tag tag);
	
	//查询
	Tag getTag(Long id);
	
	//以名字查询
	Tag getTagByName(String name);
	
	//分页查询
	Page<Tag> listTag(Pageable pageable);
	
	//查询全部
	List<Tag> ListTag();
	
	//查询最多流览的分类
	List<Tag> ListTagTop(Integer size);
	
	//id查询
	List<Tag> ListTag(String ids);
	
	//修改
	Tag updateTag(Long id,Tag tag);
	
	//删除
	void deleteTag(Long id);
}
