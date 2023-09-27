package cn.chenyilei.aec.domain.excel;

import cn.chenyilei.aec.core.excel.AecPageReadListener;
import cn.chenyilei.aec.core.excel.AecPageReadListenerContext;

import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2023/09/26 15:00
 */
public interface AecExcelService {

    /**
     * parse
     * for each stream read
     */

    public void readParse(InputStream inputStream, AecPageReadListenerContext context);
}
