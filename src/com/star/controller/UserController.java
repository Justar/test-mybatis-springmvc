package com.star.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.star.inter.IUserOperation;
import com.star.model.Article;

@Controller
@RequestMapping("/article")
public class UserController {
    @Autowired
    IUserOperation userMapper;

    @RequestMapping("/list")
    public ModelAndView listall(HttpServletRequest request,HttpServletResponse response){
        List<Article> articles=userMapper.getUserArticles(1); 
        ModelAndView mav=new ModelAndView("list");
        mav.addObject("articles",articles);
        //mav.setViewName("/article/list");
        return mav;
    }
    
    @RequestMapping("/list.action")
	public String home(Map<String,Object> model) throws Exception{
    	List<Article> articles=userMapper.getUserArticles(1); 
		model.put("articles",articles);
		return "/article/list"; //同 return "list";
	}
}

