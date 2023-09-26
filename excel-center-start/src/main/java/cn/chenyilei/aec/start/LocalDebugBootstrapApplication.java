package cn.chenyilei.aec.start;

import cn.chenyilei.aec.common.constants.AecConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author chenyilei
 * @date 2023/09/25 17:05
 */
@SpringBootApplication(scanBasePackages = "cn.chenyilei.aec")
public class LocalDebugBootstrapApplication implements CommandLineRunner {

    public static void main(String[] args) throws IOException {
        org.apache.poi.openxml4j.util.ZipSecureFile.setMinInflateRatio(0.001); //更宽松一点
        System.setProperty(AecConstant.AEC_ENV_KEY, AecConstant.AEC_ENV_LOCAL);
        System.setProperty("spring.profiles.active", "local");
        /**
         * server.port=7777
         * spring.cloud.nacos.config.enabled=false
         * spring.cloud.nacos.discovery.enabled=false
         */
        File file = new File("/Users/yileichen/Desktop/async-excel-center.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));

        SpringApplication.run(LocalDebugBootstrapApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //nacos 没启动 会报错
//        System.err.println(discoveryClient.getServices());
    }
}
