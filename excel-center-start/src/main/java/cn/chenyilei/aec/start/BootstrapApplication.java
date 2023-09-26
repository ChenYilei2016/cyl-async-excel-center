package cn.chenyilei.aec.start;

import cn.chenyilei.aec.common.constants.AecConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenyilei
 * @date 2023/09/25 17:05
 */
@SpringBootApplication(scanBasePackages = "cn.chenyilei.aec")
public class BootstrapApplication {

    public static void main(String[] args) {
        org.apache.poi.openxml4j.util.ZipSecureFile.setMinInflateRatio(0.001); //更宽松一点
        System.setProperty(AecConstant.AEC_ENV_KEY, AecConstant.AEC_ENV_NORMAL);
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
