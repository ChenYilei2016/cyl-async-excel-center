package cn.chenyilei.aec.infrastructure.oss;

import cn.chenyilei.aec.domain.oss.AecOssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/26 15:17
 */
@Slf4j
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
    public FileInputStream download(String url, Map<String, Object> runtimeParams) {
        String filePath = basePath + url;
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void upload(String path, InputStream inputStream, Map<String, Object> runtimeParams) {
        String filePath = basePath + path;
        File file = new File(filePath);

        FileOutputStream downloadFile = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            downloadFile = new FileOutputStream(filePath);
            IOUtils.copy(inputStream, downloadFile);
        } catch (IOException e) {
            log.error("LocalFileStore save failed, path:{}", path);
            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(downloadFile, null);
        }
    }

    @Override
    public void remove(String path, Map<String, Object> runtimeParams) {
        String filePath = basePath + path;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        file.delete();
    }

    @Override
    public boolean exists(String path, Map<String, Object> runtimeParams) {
        String filePath = basePath + path;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

}
