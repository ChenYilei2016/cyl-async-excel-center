package cn.chenyilei.aec.domain.oss;

import java.io.InputStream;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/26 14:46
 */
public interface AecOssService {

    /**
     * downloadExcel
     */
    InputStream download(String url, Map<String, Object> runtimeParams);


    /**
     * upload
     */
    void upload(String path, InputStream inputStream, Map<String, Object> runtimeParams);

    void remove(String path, Map<String, Object> runtimeParams);

    boolean exists(String path, Map<String, Object> runtimeParams);
}
