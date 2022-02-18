package com.zjc.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论类
 */
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    
    private String nickname;//昵称
    private String email;//邮箱
    private String content;//评论内容
    private String avatar;//头像
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//评论时间
    
    @ManyToOne
    private Blog blog;
    
    //一个评论 多个子类
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();//子类评论
    
    //一个评论 一个父类
    @ManyToOne
    private Comment parentComment;//父类评论
    
    private boolean aminComment;//是否为博主
 
    /*构造器以及getset toString*/
    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    
   

    public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
	
	
	public List<Comment> getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(List<Comment> replyComments) {
		this.replyComments = replyComments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}
	
	
	
	public boolean isAminComment() {
		return aminComment;
	}

	public void setAminComment(boolean aminComment) {
		this.aminComment = aminComment;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", nickname=" + nickname +
				", email=" + email + ", content=" + content
				+ ", avatar=" + avatar + ", createTime=" 
				+ createTime + ", blog=" + blog + ", replyComments="
				+ replyComments + ", parentComment=" + parentComment +
				", aminComment=" + aminComment + "]";
	}

	
}
