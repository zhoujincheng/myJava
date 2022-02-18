package com.zjc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjc.po.Type;

/**
 * 分类业务接口
 * @author 周金城
 *
 */
public interface TypeService {
	
	//新增
	Type saveType(Type type);
	
	//查询
	Type getType(Long id);
	
	//以名字查询
	Type getTypeByName(String name);
	
	//分页查询
	Page<Type> listType(Pageable pageable);
	
	//全部查询
	List<Type> listType();
	
	//查询最多流览的分类
	List<Type> listTypeTop(Integer size);
	
	//修改
	Type updateType(Long id,Type type);
	
	//删除
	void deleteType(Long id);
}
