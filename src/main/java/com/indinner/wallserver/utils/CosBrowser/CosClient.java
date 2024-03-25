package com.indinner.wallserver.utils.CosBrowser;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;


@Component
public class CosClient {
    static COSClient cosClient;

    static {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "";
        String secretKey = "";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    public static COSClient getCosClient() {
        return cosClient;
    }
}
