package cn.chenyilei.aec.infrastructure.config;

import cn.chenyilei.aec.common.constants.AecConstant;
import cn.chenyilei.aec.infrastructure.oss.AecLocalFileOssServiceImpl;
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
@ConditionalOnProperty(value = AecConstant.AEC_ENV_KEY, havingValue = AecConstant.AEC_ENV_LOCAL)
public class LocalDomainServiceConfiguration {
    public LocalDomainServiceConfiguration() {
        log.info(">>> local config init");
    }


    @Bean
    public AecLocalFileOssServiceImpl aecOssService(AecOssConfigProperties aecOssConfigProperties){
        return new AecLocalFileOssServiceImpl(aecOssConfigProperties.getLocal().getBasePath());
    }

}
