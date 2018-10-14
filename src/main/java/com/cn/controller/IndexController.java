package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("goto.action")
	public String toIndex(String togo) {
		return togo;
	}
}
