package cn.chenyilei.aec.infrastructure.config;

import cn.chenyilei.aec.common.constants.AecConstant;
import cn.chenyilei.aec.infrastructure.oss.AliyunOssServiceImpl;
import cn.chenyilei.aec.infrastructure.oss.AecOssConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2023/09/26 11:24
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = AecConstant.AEC_ENV_KEY, havingValue = AecConstant.AEC_ENV_NORMAL, matchIfMissing = true)
public class NormalDomainServiceConfiguration {
    public NormalDomainServiceConfiguration() {
        log.info(">>> normal config init");
    }


    @Bean
    public AliyunOssServiceImpl aecOssService(AecOssConfigProperties aecOssConfigProperties) {
        AecOssConfigProperties.AliyunOss aliyunOss = aecOssConfigProperties.getAliyunOss();
        AliyunOssServiceImpl aecAliyunOssService = new AliyunOssServiceImpl(
                aliyunOss.getEndpoint(),
                aliyunOss.getAccessKeyId(),
                aliyunOss.getAccessKeySecret(),
                aliyunOss.getBucketName()
        );
        return aecAliyunOssService;
    }
}
