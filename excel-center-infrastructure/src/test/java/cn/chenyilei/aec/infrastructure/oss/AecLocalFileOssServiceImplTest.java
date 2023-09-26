package cn.chenyilei.aec.infrastructure.oss;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author chenyilei
 * @date 2023/09/26 16:12
 */
public class AecLocalFileOssServiceImplTest {

    static AecLocalFileOssServiceImpl aecLocalFileOssService = new AecLocalFileOssServiceImpl(null);


    @org.junit.Test
    public void save() throws FileNotFoundException {
        CharSequenceInputStream hello = new CharSequenceInputStream("hello", Charset.defaultCharset());
        aecLocalFileOssService.upload("a.txt", hello);
    }

    @org.junit.Test
    public void download() throws IOException {
        FileInputStream download = aecLocalFileOssService.download("a.txt");

        System.err.println(download);

        System.err.println(IOUtils.toString(download, Charset.defaultCharset()));
    }


}