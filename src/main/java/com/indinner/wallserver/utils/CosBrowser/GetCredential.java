package com.indinner.wallserver.utils.CosBrowser;

import cn.hutool.json.JSONObject;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Data
@Component
@Configuration
@Log4j2
@ConfigurationProperties(prefix = "cos")
public class GetCredential {

    private String secretId;

    private String secretKey;

    private String regional;

    public Response getCredential() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();

        try {


            // 云 api 密钥 SecretId
            config.put("secretId", secretId);
            // 云 api 密钥 SecretKey
            config.put("secretKey", secretKey);
            System.out.println(secretId);

            // 临时密钥有效时长，单位是秒
            config.put("durationSeconds", 180);

            // 换成你的 bucket
            config.put("bucket", "ak-1302363069");
            // 换成 bucket 所在地区
            config.put("region", regional);

            // 可以通过 allowPrefixes 指定前缀数组, 例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
            config.put("allowPrefixes", new String[] {
                    "*",
                    "*"
            });

            // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[] {
                    // 简单上传
                    "name/cos:PutObject",
                    "name/cos:PostObject",
                    // 分片上传
                    "name/cos:InitiateMultipartUpload",
                    "name/cos:ListMultipartUploads",
                    "name/cos:ListParts",
                    "name/cos:UploadPart",
                    "name/cos:CompleteMultipartUpload"
            };
            config.put("allowActions", allowActions);

            Response response = CosStsClient.getCredential(config);
            log.info("临时密钥获取结果：{}",new JSONObject(response).toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
