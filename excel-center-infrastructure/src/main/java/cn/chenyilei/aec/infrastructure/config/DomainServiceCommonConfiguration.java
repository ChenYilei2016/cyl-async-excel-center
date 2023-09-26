package cn.chenyilei.aec.infrastructure.config;

import cn.chenyilei.aec.infrastructure.excel.AecEasyExcelServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2023/09/26 16:47
 */

@Configuration
public class DomainServiceCommonConfiguration {

    @Bean
    public AecEasyExcelServiceImpl aecExcelService() {
        return new AecEasyExcelServiceImpl();
    }
}
