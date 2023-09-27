package cn.chenyilei.center.sample.easyexcel.read;

import cn.chenyilei.aec.common.utils.ResourceLoadUtil;
import cn.chenyilei.aec.core.excel.AecPageReadListener;
import cn.chenyilei.aec.core.excel.AecPageReadListenerContext;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import org.junit.Test;

/**
 * @author chenyilei
 * @date 2023/09/27 14:44
 */
public class TestPageReadExcel {

    @Test
    public void test1() {
        AecPageReadListenerContext aecPageReadListenerContext = new AecPageReadListenerContext((cachedDataList, analysisContext, context) -> {
            System.err.println(cachedDataList);
        });
        aecPageReadListenerContext.setBatchCount(5); //page 5
        AecPageReadListener readListener = new AecPageReadListener(aecPageReadListenerContext);
        ExcelReaderBuilder read = EasyExcel.read(ResourceLoadUtil.readFile("classpath:excel/demoExcel.xlsx"), readListener);

        read.doReadAll();
    }
}
