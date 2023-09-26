package cn.chenyilei.aec.infrastructure.oss;

import cn.chenyilei.aec.domain.oss.AecOssService;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;

/**
 * @author chenyilei
 * @date 2023/09/26 15:17
 */
public class AecLocalFileOssServiceImpl implements AecOssService {

    private String basePath;

    public AecLocalFileOssServiceImpl(String inputBasePath) {
        this.basePath = inputBasePath;
        if (this.basePath == null || this.basePath.isEmpty()) {
            this.basePath = System.getProperty("user.home") + File.separator + "aec" + File.separator;
        }
        File folder = new File(this.basePath);
        if (!folder.exists() || !folder.isDirectory()) {
            boolean mkdirs = folder.mkdirs();
            if (!mkdirs) {
                throw new IllegalArgumentException("folder " + inputBasePath + " cannot create");
            }
        }
    }

    @Override
    public File download(String url) {
        return null;
    }

}
