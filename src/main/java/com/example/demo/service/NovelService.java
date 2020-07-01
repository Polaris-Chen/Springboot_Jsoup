package com.example.demo.service;


import com.example.demo.Entity.Novel;
import org.jsoup.select.Selector;

import java.util.List;

public interface NovelService {
    int insertNovel(Novel novel);

    List<Novel> queryAllNovel();

    Novel queryOne(String name ) throws Selector.SelectorParseException;

    String queryContent(String name);

    int update(String name);
}
