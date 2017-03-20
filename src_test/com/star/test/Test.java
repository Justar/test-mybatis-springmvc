package com.star.test;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.star.inter.IUserOperation;
import com.star.model.Article;
import com.star.model.User;

public class Test {
    private static SqlSessionFactory sqlSessionFactory;
    private static Reader reader; 

    static{
        try{
            reader    = Resources.getResourceAsReader("Configuration.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	test1();
        //test2();
        //getUserList("%");
    	//addUser();
    	//updateUser();
    	//deleteUser(4);
    	//getUserArticles(1);
    }

    public static SqlSessionFactory getSession(){
        return sqlSessionFactory;
    }
    
    public static void test1(){
    	SqlSession session = sqlSessionFactory.openSession();
        try {
        	User user = (User) session.selectOne("com.star.inter.IUserOperation.selectUserByID", 1);
        	System.out.println(user.getUserAddress());
        	System.out.println(user.getUserName());
        } finally {
        	session.close();
        }
    }
    
    public static void test2(){
    	SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(1);
            System.out.println(user.getUserAddress());
            System.out.println(user.getUserName());
        } finally {
            session.close();
        }
    }
    
    public static void getUserList(String userName){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<User> users = userOperation.selectUsers(userName);
            for(User user:users){
                System.out.println(user.getId()+":"+user.getUserName()+":"+user.getUserAddress());
            }
            
        } finally {
            session.close();
        }
    }
    
    /**
     * 测试增加,增加后，必须提交事务，否则不会写入到数据库.
     */
    public static void addUser(){
        User user=new User();
        user.setUserAddress("人民广场");
        user.setUserName("飞鸟");
        user.setUserAge(80);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);
            userOperation.addUser(user);
            session.commit();
            System.out.println("当前增加的用户 id为:"+user.getId());
        } finally {
            session.close();
        }
    }
    
    public static void updateUser(){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);
            User user = userOperation.selectUserByID(4);            
            user.setUserAddress("原来是魔都的浦东创新园区");
            userOperation.updateUser(user);
            session.commit();
            
        } finally {
            session.close();
        }
    }
    
    /**
     * 删除数据，删除一定要 commit.
     * @param id
     */
    public static void deleteUser(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            userOperation.deleteUser(id);
            session.commit();            
        } finally {
            session.close();
        }
    }
    
    public static void getUserArticles(int userid){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            IUserOperation userOperation=session.getMapper(IUserOperation.class);           
            List<Article> articles = userOperation.getUserArticles(userid);
            for(Article article:articles){
                System.out.println(article.getTitle()+":"+article.getContent()+
                        ":作者是:"+article.getUser().getUserName()+":地址:"+
                         article.getUser().getUserAddress());
            }
        } finally {
            session.close();
        }
    }
}
