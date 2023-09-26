package cn.chenyilei.aec.validate;

import cn.chenyilei.aec.common.constants.AecConstant;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chenyilei
 * @date 2023/09/26 15:30
 * 运行前校验监听器
 */
public class AecVerifyRunListener implements SpringApplicationRunListener {

    private final SpringApplication springApplication;
    private final String[] args;

    public AecVerifyRunListener(org.springframework.boot.SpringApplication springApplication, String[] args) {
        this.springApplication = springApplication;
        this.args = args;
    }

    @Override
    public void starting() {
        if (System.getProperty(AecConstant.AEC_ENV_KEY) == null) {
            throw new IllegalArgumentException("not exist : cn.chenyilei.aec.common.constants.AecConstant.AEC_ENV_KEY");
        }
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        SpringApplicationRunListener.super.failed(context, exception);
    }
}
