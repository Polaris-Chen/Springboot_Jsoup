package com.example.demo.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@org.springframework.stereotype.Controller
public class novelController {



    @Autowired
    private Spider spider;

    @RequestMapping("/")
    public String index(){
        return "index";
    }



    @RequestMapping("/download")
    public String data(String url,Model model) throws SQLException, IOException, ClassNotFoundException {

    spider.start(url);
        String nameurl=System.getProperty("user.dir")+"/src/main/resources/static/text/"+spider.novleName+".txt";
        model.addAttribute("link",nameurl);
        model.addAttribute("name",spider.novleName);
        return "download";
    }


    @RequestMapping("/file")
    @RestController
    public class FileDealController {

        @RequestMapping(value = "filename")
        public void download(
                @RequestParam("fileName") String filename
        ) throws IOException {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            // 设置信息给客户端不解析
            String type = new MimetypesFileTypeMap().getContentType(filename);

            // 设置contenttype，即告诉客户端所发送的数据属于什么类型
            response.setHeader("Content-type",type);
            // 设置编码
            String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
            FileUtil.download(filename, response);
        }
    }

}
