package com.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.entity.Sheep;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
//@Repository
public interface SheepMapper {
    @Insert("insert into sheep(name,age,sex) values(#{name},#{age},#{sex}) ")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
        //id自增长
    void save(Sheep sheep);

    @Delete("delete *from sheep where id =#{id}")
    void delete(int id);

//    List<Sheep> findByXml(@Param("age") int age);

    @Select("select *from sheep where age = #{age}")
    List<Sheep> getByAge(int age);


}