package com.zjc.vo;
/**
 * 博客动态查询
 * @author 周金城
 *
 */
public class BlogQuery {
	private String title;
	private Long typeId;
	private Long userId;
	private boolean recommend;
	private boolean visitor;
	
	//构造器以及set get
	public BlogQuery() {}
	
	

	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public boolean isVisitor() {
		return visitor;
	}



	public void setVisitor(boolean visitor) {
		this.visitor = visitor;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public boolean isRecommend() {
		return recommend;
	}

	
	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

		
	
}
