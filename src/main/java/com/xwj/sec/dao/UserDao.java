package com.xwj.sec.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xwj.sec.entity.User;

/**
 * mybatis，使用注解操作
 * 
 * @author xuwenjin
 */
@Mapper
public interface UserDao {

	@Select("select * from user where id = #{id}")
	public User getById(@Param("id") int id);

	@Insert("insert into user(id, name) values(#{id}, #{name})")
	public int insert(User user);

}
