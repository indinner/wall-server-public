package com.indinner.wallserver.entity;

import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public enum FontEnums {
    SIMSUN("SimSun", "simsun.ttf"),
    ;
    private String name;
    private String path;
    private Font font;

    FontEnums(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public Font getFont() {
        if (null == font) {
            InputStream is = null;
            BufferedInputStream bis = null;
            try {
                is=new ClassPathResource(path).getInputStream();
                //is = Resources.getResourceAsStream(path);
                bis = new BufferedInputStream(is);
                // 可能会报 "java.awt.FontFormatException: bad table, tag=23592960" 错误..
                font = Font.createFont(Font.TRUETYPE_FONT, bis);
            } catch (Exception e) {
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception e) {
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                }
            }
        }
        return null;
    }
}
