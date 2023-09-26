package cn.chenyilei.aec.infrastructure.oss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2023/09/26 15:39
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "aec.oss")
public class AecOssConfigProperties {

    public static final String BUCKET_NAME_KEY = "BUCKET_NAME_KEY";

    @NestedConfigurationProperty
    private AliyunOss aliyunOss = new AliyunOss();

    @NestedConfigurationProperty
    private Local local = new Local();


    @Getter
    @Setter
    public static class AliyunOss {
        /**
         * 访问key
         */
        private String accessKeyId;
        /**
         * 访问秘钥
         */
        private String accessKeySecret;
        /**
         * 站点
         */
        private String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        /**
         * 桶名称
         */
        private String bucketName = "async-excel-center";
    }

    @Getter
    @Setter
    public static class Local {
        /**
         * 本地基础路径
         */
        private String basePath;
    }

}
