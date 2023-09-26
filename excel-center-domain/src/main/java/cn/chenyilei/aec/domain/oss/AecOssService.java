package cn.chenyilei.aec.domain.oss;

import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2023/09/26 14:46
 */
public interface AecOssService {

    /**
     * downloadExcel
     */
    InputStream download(String url);


    /**
     * upload
     */
    void upload(String path, InputStream inputStream);
}
