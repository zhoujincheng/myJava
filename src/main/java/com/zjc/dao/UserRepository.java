package com.zjc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zjc.po.User;

/**
 * 用户数据库操作接口
 * @author 周金城
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsernameAndPassword(String username,String password);
	
	User findByEmail(String email);
}
