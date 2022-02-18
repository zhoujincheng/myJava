package com.zjc.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客类
 */
@Entity //表明这是实体类，和数据库建立对应
@Table(name="t_blog")//设置数据库表名
public class Blog {

	@Id//表明这是主键
	@GeneratedValue//id自动生成
    private Long id;

    private String title;//标题
    
    @Basic(fetch = FetchType.LAZY)//使用时才加载
    @Lob//设置为大字段
    private String content;//内容
    
    @Basic(fetch = FetchType.LAZY)//使用时才加载
    @Lob//设置为大字段
    private String firstPicture;//首图地址
    private String flag="原创";//标记
    private Integer views=0;//流览次数
    private boolean appreciation;//赞赏是否开启
    private boolean shareStatement;//版权是否开启
    private boolean commentabled;//评论是否开启
    private boolean published;//发布
    private boolean recommend;//是否推荐
    
    @Temporal(TemporalType.TIMESTAMP)//设置时间类型对应数据库表结构
    private Date createTime;//创建时间
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间
    
    @ManyToOne // 多对一 多个博客对应一个类型 主动维护
    private Type type;
    
    //多对多 集联新增 博客增加一个新标签 数据库也会增加一个新标签
    @ManyToMany(cascade = {CascadeType.PERSIST} )
    private List<Tag> tags=new ArrayList<>();
    
    @Transient
    private String tagIds;
    
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "blog")//一对多，根据comment里的blog建立对应关系 被维护
    private List<Comment> comments=new ArrayList<>();
 
    /*构造器以及getset toString*/
    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    

    public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
  public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

	@Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
