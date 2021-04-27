package com.example.jogo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/post")
public class FileController {
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> getFile(HttpServletRequest httpServletRequest)  {
        try{
            MultipartRequest request = (MultipartRequest)httpServletRequest;
            String name = request.getFile("file").getOriginalFilename();
            FileOutputStream fos = new FileOutputStream(new File("/usr/local/var/files/"+name));
            FileInputStream fis = (FileInputStream) request.getFile("file").getInputStream();
            byte[] bytes = new byte[1024];
            int len=0;
            while((len=fis.read(bytes))!=-1)
                fos.write(bytes,0,len);
            fis.close();
            fos.close();
            System.out.println("上传成功");
        }catch (Exception e){
            System.out.println(e);
        }
        return new HashMap<>(){{put("msg","收到");}};
    }


}
