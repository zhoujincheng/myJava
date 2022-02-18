package com.zjc.service;

import com.zjc.po.User;

/**
 * 登录业务接口
 * @author 周金城
 *
 */
public interface UserService {
	User checkUser(String username,String password);
	
	User addUser(String username,String nickname,String password,String email);
	
	boolean checkEmail(String email);
}
