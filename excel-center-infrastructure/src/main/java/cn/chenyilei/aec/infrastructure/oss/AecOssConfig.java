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
public class AecOssConfig {

    @NestedConfigurationProperty
    private AliyunOss aliyunOss = new AliyunOss();

    @NestedConfigurationProperty
    private Local local = new Local();


    @Getter
    @Setter
    public static class AliyunOss {

    }
    @Getter
    @Setter
    public static class Local {
        private String basePath;
    }

}
