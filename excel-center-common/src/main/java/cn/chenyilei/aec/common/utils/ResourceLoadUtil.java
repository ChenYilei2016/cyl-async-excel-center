package cn.chenyilei.aec.common.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2023/09/22 14:09
 */
@UtilityClass
public class ResourceLoadUtil {

    FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();


    @SneakyThrows
    public File readFile(String path) {
        Resource resource = resourceLoader.getResource(path);
        return resource.getFile();
    }

    @SneakyThrows
    public InputStream readFileStream(String path) {
        Resource resource = resourceLoader.getResource(path);
        return resource.getInputStream();
    }
}
