package cn.chenyilei.aec.common.utils;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2023/09/22 14:28
 */
public class ResourceLoadUtilTest {

    @Test(expected = java.io.FileNotFoundException.class)
    public void readFile() {
        File file = ResourceLoadUtil.readFile("xxxxNo404");
        System.err.println(file);
    }

    @Test(expected = java.io.FileNotFoundException.class)
    public void readFileStream() {
        InputStream xxxxNo404 = ResourceLoadUtil.readFileStream("xxxxNo404");
    }

}