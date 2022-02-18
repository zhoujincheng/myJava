package com.zjc.service;

import java.util.ArrayList;
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
import com.zjc.dao.TagsRepository;
import com.zjc.po.Tag;

/**
 * 标签业务实现
 * @author 周金城
 *
 */
@Service
public class TagsServiceImpl implements TagsService {
	
	@Autowired
	private TagsRepository tagsRepository;
	
	@Transactional
	@Override
	public Tag saveTag(Tag tag) {		
		return tagsRepository.save(tag);
	}
	
	@Transactional
	@Override
	public Tag getTag(Long id) {		
		return tagsRepository.findById(id).get();
	}
	
	
	
	@Transactional
	@Override
	public Tag getTagByName(String name) {
		
		return tagsRepository.findByName(name); 
	}

	@Transactional
	@Override
	public Page<Tag> listTag(Pageable pageable) {		
		return tagsRepository.findAll(pageable);
	}		
	
	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t=tagsRepository.findById(id).get();
		if(t==null) {
			throw new NotFoundException("找不到该分类");
		}
		BeanUtils.copyProperties(tag, t);
		return tagsRepository.save(t);
	}
	
	@Transactional
	@Override
	public void deleteTag(Long id) {
		tagsRepository.deleteById(id);
	}

	@Override
	public List<Tag> ListTag() {
		
		return tagsRepository.findAll();
	}

	@Override
	public List<Tag> ListTag(String ids) {
		
		return tagsRepository.findAllById(convertToList(ids));
	}
	
	//字符串转数组
	 private List<Long> convertToList(String ids) {
	        List<Long> list = new ArrayList<>();
	        if (!"".equals(ids) && ids != null) {
	            String[] idarray = ids.split(",");
	            for (int i=0; i < idarray.length;i++) {
	                list.add(new Long(idarray[i]));
	            }
	        }
	        return list;
	    }

	@Override
	public List<Tag> ListTagTop(Integer size) {
		Sort sort=new Sort(Sort.Direction.DESC,"blogs.size");
		Pageable pageable=PageRequest.of(0,size,sort);
		return tagsRepository.findTop(pageable);
	}

}
