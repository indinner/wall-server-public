package com.indinner.wallserver.utils;

import com.indinner.wallserver.entity.CreatePreviewImg;
import com.indinner.wallserver.utils.CosBrowser.PutFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 16:45
 * @Version 1.0
 * @Doc:
 */
@Service
@Log4j2
public class CreateImgByWallUtils {

    /**
     * 通过类加载器的方式获取模板
     * springboot项目在部署的时候会打包成jar，打包成jar以后在使用freemaker时会出现以下报错：
     *           cannot be resolved to absolute file path because it does not reside in the file system: jar
     * 通过以下 setClassLoaderForTemplateLoading() 方法设置成类加载器的方式，可以解决上述无法访问模板路径的问题
     * @param template
     * @param map
     * @return
     * @throws IOException
     */
    private String getTemplateByClassLoader(String template, Map<String, Object> map) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setClassicCompatible(true);
        Template temp = cfg.getTemplate(template);
        StringWriter stringWriter = new StringWriter();
        temp.process(map, stringWriter);
        stringWriter.flush();
        stringWriter.close();
        String result = stringWriter.getBuffer().toString();
        return result;
    }

    /**
     * ftl模板生成图片接口
     *
     * @param filename    生成图片名称
     * @param templateUrl ftl模板路径
     * @param template    ftl模板名称
     * @param map         模板占位符数据
     * @throws Exception
     */
    public String turnImage(String template, Map<String, Object> map) throws Exception {
        String html =getTemplateByClassLoader(template, map);
        byte[] bytes = html.getBytes("UTF-8");

        ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(bin);

        //加载自定义字体，解决生成图片title处汉字展示不正常问题
        /*InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("font/simsun.ttf");
        Font font = Font.createFont(TRUETYPE_FONT, inputStream);
        AWTFontResolver awtFontResolver = new AWTFontResolver();
        awtFontResolver.setFontMapping("simsun", font);*/

        Java2DRenderer renderer = new Java2DRenderer(document, 1080, -1);
        //renderer.getSharedContext().setFontResolver(awtFontResolver);
        /*Map<Object,Object> map1=new HashMap<>();
        map1.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map1.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_                                           ON);
        map1.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        map1.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        map1.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        map1.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        map1.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        renderer.setRenderingHints(map1);*/
        BufferedImage img = renderer.getImage();
        // 转成流上传至服务器
        ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", dataOutputStream);
        byte[] bts = dataOutputStream.toByteArray();
        String imgUrl = PutFile.upFile("",bts, UUID.randomUUID().toString(),"","");
        return imgUrl;

    }

    /**
     * 处理过长的文本
     * @param input
     * @return
     */
    public List<String> splitString(String input, int number) {
        List<String> result = new ArrayList<>();
        int length = input.length();
        if (length <= number) {
            result.add(input);
        } else {
            for (int i = 0; i < length; i += number) {
                int endIndex = i + number;
                if (endIndex > length) {
                    endIndex = length;
                }
                result.add(input.substring(i, endIndex));
            }
        }
        return result;
    }

    public String createImg(CreatePreviewImg createPreviewImg) throws Exception {
        log.info("createPreviewImg:{}",createPreviewImg);
        Map<String,Object> map=new HashMap<>();
        List<String> msg_list=createPreviewImg.getMessages();
        List<String> imgList=createPreviewImg.getImgUrlList();
        String time=createPreviewImg.getCreateTime();
        String tag=createPreviewImg.getTag();
        String wechat=createPreviewImg.getWechat();
        String qq=createPreviewImg.getQq();
        String headImg=createPreviewImg.getHeadImg();
        String qrcode=createPreviewImg.getQrCode();
        String bgColor=createPreviewImg.getBgColor();
        if(wechat.length()>0){
            msg_list.add("微信:"+wechat);
        }
        if(qq.length()>0){
            msg_list.add("QQ:"+qq);
        }


        map.put("bg","https://cdn.indinner.com/backgroundcolor/"+bgColor+".jpeg");
        map.put("imgList",imgList);
        map.put("time",time);
        map.put("replay","墙墙收到啦~");
        map.put("messages",msg_list);
        map.put("tag",tag);
        map.put("headImg",headImg);
        map.put("qrCode",qrcode);
        return turnImage("test.ftl", map);
    }

}
