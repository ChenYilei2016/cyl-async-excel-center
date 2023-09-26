package cn.chenyilei.aec.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyilei
 * @date 2023/09/25 17:05
 */
@SpringBootApplication
public class BootstrapApplication {

    public static void main(String[] args) {
        org.apache.poi.openxml4j.util.ZipSecureFile.setMinInflateRatio(0.001); //更宽松一点
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
