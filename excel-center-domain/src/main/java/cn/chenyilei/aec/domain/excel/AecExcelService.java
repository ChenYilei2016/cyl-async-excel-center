package cn.chenyilei.aec.domain.excel;


import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2023/09/26 15:00
 */
public interface AecExcelService {

    /**
     * parse
     * for each stream read
     * and oss
     */

    public void readParse(InputStream inputStream);


}
