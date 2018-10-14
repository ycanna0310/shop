package com.cn.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.domain.User;
import com.cn.dto.UserVo;
import com.cn.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("login.action")
	public String login(String username,String password,Model model,HttpSession session) {
		User user = userService.login(username, password);
		if(user != null) {
			session.setAttribute("user", user);
			return "default";
		}else {
			model.addAttribute("loginInfo", "用户名或密码错误");
			return "login";
		}
	}
	
	@RequestMapping("checkUsername.action")
	@ResponseBody
	public Map checkUsername(String username) {
		boolean isExist = userService.checkUsername(username);
		Map map = new HashMap();
		map.put("isExist", isExist);
		return map;
		
	}
	
	@RequestMapping("register.action")
	public String register(UserVo uservo) {
		User user=new User();
		user.setUsername(uservo.getUsername());
		user.setPassword(uservo.getPassword());
		user.setUid(UUID.randomUUID().toString());
		boolean isSuccess = userService.register(user);
		if(isSuccess) {
			return "login";
		}else {
			return "registerFail";
		}
	}
	
}
