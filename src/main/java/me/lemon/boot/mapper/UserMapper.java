package me.lemon.boot.mapper;

import me.lemon.boot.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (name,password) VALUES (#{name},#{password})")
    int insert(User user);
    @Update("UPDATE users SET name=#{name} password=#{password} WHERE id=#{id}")
    int updateById(User user);
    @Update("UPDATE users SET password=#{password} WHERE name=#{name}")
    int updateByName(User user);
    @Delete("DELETE FROM users WHERE id=#{id}")
    int deleteById(User user);
    @Select("SELECT * FROM users WHERE id=#{id}")
    User findById(int id);
    @Select("SELECT * FROM users;")
    List<User> findAll();
    @Select("SELECT * FROM users WHERE name=#{name}")
    User findByName(String name);
}
