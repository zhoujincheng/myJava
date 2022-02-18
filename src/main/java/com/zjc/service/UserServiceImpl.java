package com.zjc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zjc.dao.UserRepository;
import com.zjc.po.User;
import com.zjc.util.MD5Utils;
/**
 * 登录业务实现
 * @author 周金城
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${comment.avatar}")
	private String avatar;
	
	@Override
	public User checkUser(String username, String password) {
		User user= userRepository.findByUsernameAndPassword(username,MD5Utils.code(password));
		return user;
	}

	@Override
	public User addUser(String username, String nickname,String password, String email) {
		User user=new User();
		user.setUsername(username);
		user.setNickname(nickname);
		password=new MD5Utils().code(password);
		user.setPassword(password);
		user.setEmail(email);
		user.setType(2);
		user.setAvatar(avatar);
		userRepository.save(user);
		return user;
	}

	@Override
	public boolean checkEmail(String email) {
		
		if(userRepository.findByEmail(email)!=null) {			
			return false;
		}
		return true;
	}

}
