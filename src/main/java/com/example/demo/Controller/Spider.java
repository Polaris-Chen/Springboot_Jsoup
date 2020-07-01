package com.example.demo.Controller;

import com.example.demo.Entity.Novel;
import com.example.demo.service.NovelServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;


@Component
public class Spider {

    @Autowired
    private NovelServiceImpl novelService;

    String novleName;

    public void start(String url) throws IOException, SQLException, ClassNotFoundException {

        String constent = findHref("章节目录", url);
        String name=findname(url,constent);
        novleName=name;
        String end = "http://www.yuetutu.com" + constent;

        if (!ifInDB(name)){
            String text=FindNovle(name,url,end);
            insertToDatabase(name,text);

        }
        else {
           createTXT(name);
        }


    }

    public void createTXT(String name) throws IOException {
        String pathname=System.getProperty("user.dir")+"/src/main/resources/static/text/"+name+".txt";
        File file = new File(pathname);
        FileWriter fwriter = null;
        System.out.println(file);

        if (!file.exists()) {
            file.createNewFile();
            String text=novelService.queryContent(name);

            fwriter = new FileWriter(pathname);
            fwriter.write(text);

            fwriter.flush();
            fwriter.close();
        }
        else {
            return;
        }
    }

    public boolean ifInDB(String name){
        try {
            Novel novel=novelService.queryOne(name);

            int times=novel.getDownloadtimes();

            novelService.update(name);
            System.out.println("haha");
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean insertToDatabase(String name,String content){

            Novel novel=new Novel();
            novel.setName(name);
            novel.setContent(content);
            novel.setDownloadtimes(1);
            novelService.insertNovel(novel);
            return true;

    }

    /************************爬取主方法开始**************************/
    public String FindNovle(String name,String url,String end) throws IOException, SQLException, ClassNotFoundException {
         String Textcontent="";

        String pathname=System.getProperty("user.dir")+"/src/main/resources/static/text/"+name+".txt";
        File file = new File(pathname);
        System.out.println(file);
        file.createNewFile();

        Writer out = new FileWriter(file);

        int over=0;
        while (over<5) {
            Document document = Jsoup.connect(url).get();
            String title = document.title();
            Elements elements = document.select("div[id=content]");
            for (Element element : elements) {
                String rawText = element.text() + "\n" + "\n";
                String text = rawText.replace(" ", "\n");
                out.write(title);
                out.write(text);
                Textcontent=Textcontent+text;
            }
            String next = "http://www.yuetutu.com" + findHref("下一章", url);
            over++;
            url=next;

//            if (next.equals(end)) {
//
//                out.close();
//                break;
//            } else {
//                url = next;
//            }
        }

        out.close();
        //setDatabase(name, pathname);
        return  Textcontent;
        //}
        //catch (Exception e){
            /*System.out.println("爬取出现了问题");
            return "错误";*/

    }
    /*********************************爬取辅助方法****************************/

    public static String findname(String starturl, String constent) throws IOException {
        Document test = Jsoup.connect(starturl).get();
        Elements xx=test.select("a[href="+constent+"]");
        String end="";
        for( Element element : xx ){//拿到小说名字
            end=element.text();
            break;


        }
        return end;
    }
    public static String findHref(String name,String starturl) throws IOException {

        Document test = Jsoup.connect(starturl).get();
        Elements xx=test.select("a[href~=^/cbook]");
        String end="";
        for( Element element : xx ){//拿到章节目录的超链接
            if (element.text().equals(name)){
                end=element.attr("href");
                break;
            }

        }
        return end;
    }
}
