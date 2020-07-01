package com.example.demo.service;

import com.example.demo.Entity.Novel;
import com.example.demo.mapper.NovelMapper;
import org.jsoup.select.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NovelServiceImpl implements NovelService {

    @Autowired
    NovelMapper mapper;

    @Override
    public int insertNovel(Novel novel) {
        mapper.insertANovel(novel);
        return 0;
    }

    @Override
    public List<Novel> queryAllNovel() {
        List<Novel> l =mapper.queryAll();

        return l;

    }

    @Override
    public Novel queryOne(String name) throws Selector.SelectorParseException {
        Novel novel=mapper.selectOne(name);
        return novel;
    }

    @Override
    public String queryContent(String name) {
        String text=mapper.selectOneContent(name);
        return text;
    }

    @Override
    public int update(String name) {
        int x=mapper.update(name);
        return x;
    }
}
