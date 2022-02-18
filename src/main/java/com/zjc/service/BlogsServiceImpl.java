package com.zjc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjc.NotFoundException;
import com.zjc.dao.BlogsRepository;
import com.zjc.po.Blog;
import com.zjc.po.Type;
import com.zjc.po.User;
import com.zjc.util.MarkdownUtils;
import com.zjc.util.MyBeanUtils;
import com.zjc.vo.BlogQuery;
/**
 * 博客业务实现
 * @author 周金城
 *
 */
@Service
public class BlogsServiceImpl implements BlogsService {
	
	@Autowired
	private BlogsRepository blogsRepository;
	
	@Override
	public Blog getBlog(Long id) {
		
		
			Blog b=blogsRepository.findById(id).get();
			return b;
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
		return	blogsRepository.findAll(new Specification<Blog>() {			
			@Override
			public Predicate toPredicate(Root<Blog> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {
				
				List<Predicate> predicate=new ArrayList<>();
				//如果有标题则加入搜索条件
				if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle()!=null) {
					predicate.add(cb.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
				}
				//如果有分类则加入搜索条件
				if(blogQuery.getTypeId()!=null) {						
					predicate.add(cb.equal(
							root.<Type>get("type").get("id"), blogQuery.getTypeId()));
				}
				//如果有推荐则加入搜索条件
				if(blogQuery.isRecommend()) {
					predicate.add(cb.equal(
							root.<Boolean>get("recommend"), blogQuery.isRecommend()));
				}
				
				//如果有用户id则加入搜索条件
				if(blogQuery.getUserId()!=null) {
					predicate.add(cb.equal(
							root.<User>get("user").get("id"), blogQuery.getUserId()));
				}
				
				//以条件进行查询
				cq.where(predicate.toArray(new Predicate[predicate.size()]));
				return null;
			}
		},pageable);
		
	}
	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if(blog.getId()==null) {			
			blog.setCreateTime(new Date());
			blog.setUpdateTime(new Date());
			blog.setViews(0);
		}else {
			blog.setUpdateTime(new Date());
		}
		return blogsRepository.save(blog);
	}
	
	@Transactional
	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b=blogsRepository.findById(id).get();
		if(b==null) {
			throw new NotFoundException("该博客不存在");
		}
		BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
		b.setUpdateTime(new Date());
		return blogsRepository.save(b);
	}
	
	@Transactional
	@Override
	public void deleteBlog(Long id) {
		
		blogsRepository.deleteById(id);		
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		
		return	blogsRepository.PublishedTrue(pageable);
	}
	

	@Override
	public List<Blog> ListRecommendBlogTop(Integer size) {
		Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
		Pageable pageable=PageRequest.of(0,size,sort);
		return blogsRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		
		return blogsRepository.findByQuery(query, pageable);
	}

	@Override
	public Blog getBlogAndConvert(Long id) {
		Blog blog=blogsRepository.findById(id).get();
		if(blog==null) {
			throw new NotFoundException("该博客找不到了/(ㄒoㄒ)/~~");
		}
		Blog b=new Blog();
		BeanUtils.copyProperties(blog, b);
		String content=b.getContent();		
		b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
		return b;
	}
	
	@Transactional
	@Override
	public void blogViewsAdd(Long id) {
		Blog b=getBlog(id);
		b.setViews(b.getViews()+1);
		updateBlog(id, b);
	}

	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		
		return blogsRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, 
            		CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		 List<String> years = blogsRepository.findGroupYear();
	        Map<String, List<Blog>> map = new HashMap<>();
	        for (String year : years) {
	            map.put(year, blogsRepository.findByYear(year));
	        }
	        return map;
	}
	
	 @Override
	    public Long countBlog() {
	        return blogsRepository.count();
	    }

	@Override
	public Page<Blog> listBlogByUserId(Pageable pageable, Long userId) {
		
		return  blogsRepository.findByUserId(pageable, userId);
	}

}
