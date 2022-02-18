package com.zjc.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjc.NotFoundException;
import com.zjc.dao.TypeRepository;
import com.zjc.po.Type;

/**
 * 分类业务实现
 * @author 周金城
 *
 */
@Service
public class TypeServiceImpl implements TypeService {
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Transactional
	@Override
	public Type saveType(Type type) {		
		return typeRepository.save(type);
	}
	
	@Transactional
	@Override
	public Type getType(Long id) {		
		return typeRepository.findById(id).get();
	}
	
	
	
	@Transactional
	@Override
	public Type getTypeByName(String name) {
		
		return typeRepository.findByName(name); 
	}

	@Transactional
	@Override
	public Page<Type> listType(Pageable pageable) {		
		return typeRepository.findAll(pageable);
	}		
	
	@Transactional
	@Override
	public Type updateType(Long id, Type type) {
		Type t=typeRepository.findById(id).get();
		if(t==null) {
			throw new NotFoundException("找不到该分类");
		}
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}
	
	@Transactional
	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}
	
	@Transactional
	@Override
	public List<Type> listType() {
		
		return typeRepository.findAll();
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable=PageRequest.of(0,size,sort);
		return typeRepository.findTop(pageable);
	}

}
