package com.indinner.wallserver.utils.CosBrowser;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.log4j.Log4j2;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

@Log4j2
public class PutFile {

    /**
     * 上传文件到腾讯云对象存储
     * @param bucketName 存储桶名字
     * @param bytes 文件二进制数据
     * @param name 文件名字
     * @param path 文件保存路径首尾不带“/”
     * @param fileType 文件类型*/
    public static String upFile(String bucketName,byte[] bytes,String name,String path,String fileType){
        COSClient cosClient= CosClient.getCosClient();
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = path+"/"+name+"."+fileType;
        // 获取文件流
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(bytes.length);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        //objectMetadata.setContentType("image/jpeg");

        PutObjectResult putObjectResult=cosClient.putObject(bucketName,key,byteArrayInputStream,objectMetadata);

        /*String etag=putObjectResult.getMetadata().getETag();

        URL url=cosClient.getObjectUrl(bucketName,key);*/
        //关闭客户端
        /*cosClient.shutdown();*/
        /*return String.valueOf(url);*/
        return "https://cdn.indinner.com/"+key;
    }

    /**
     * 接受base64字符串,返回byte数组
     * @param imgBase64 图片的base64文件
     * */
    public static byte[] getByteInBase64(String imgBase64){
        imgBase64 = imgBase64.replaceAll("data:image\\/\\w+;base64,", "");
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(imgBase64);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
            return imageByte;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("base64解码失败");
            return null;
        }
    }

}
