package com.example.demo.mapper;

import com.example.demo.Entity.Novel;
import org.apache.ibatis.annotations.*;
import org.jsoup.select.Selector;

import java.util.List;

@Mapper
public interface NovelMapper {


    @Insert("INSERT INTO NOVEL VALUES(#{name},#{content},#{downloadtimes})")
        public int insertANovel(Novel novel);



    @Select("SELECT * FROM Novel")
        List<Novel> queryAll();



    @Select("SELECT * FROM Novel WHERE name=#{name}")
    Novel selectOne(String name) ;

    @Select("SELECT content FROM Novel WHERE name=#{name}")
    String selectOneContent(String name);


    @Update("UPDATE Novel SET downloadtimes=downloadtimes+1 WHERE name=#{name}")
        int update(String name);
    }

