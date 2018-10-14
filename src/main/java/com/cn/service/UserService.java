package com.cn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.dao.UserMapper;
import com.cn.domain.User;
import com.cn.domain.UserExample;
import com.cn.domain.UserExample.Criteria;

@Service
public class UserService {
	@Autowired
	private UserMapper userDao;
	
	public User login(String username, String password) {
		UserExample example=new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		createCriteria.andPasswordEqualTo(password);
		List<User> selectByExample = userDao.selectByExample(example);
		if(selectByExample.size()>0) {
			return selectByExample.get(0);
		}else {
			return null;
		}
	}
	
	public boolean checkUsername(String username) {
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(username);
		int countByExample = userDao.countByExample(example);
		return countByExample>0 ? true : false;
	}
	
	public boolean register(User user) {
		int row = userDao.insertSelective(user);
		return row>0 ? true : false;
	}
	
}
