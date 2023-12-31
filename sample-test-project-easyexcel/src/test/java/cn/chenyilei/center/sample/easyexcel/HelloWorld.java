package cn.chenyilei.center.sample.easyexcel;

import cn.chenyilei.aec.common.utils.ResourceLoadUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.junit.Test;

import java.io.File;

/**
 * @author chenyilei
 * @date 2023/09/22 14:02
 */
public class HelloWorld {


    /**
     * 实际还是读取一遍数据
     */
    @Test
    public void hello_读取行数(){

        File file = ResourceLoadUtil.readFile("classpath:excel/demoExcel.xlsx");

        System.err.println(file.exists());
        System.err.println(file.getAbsolutePath());

        ExcelReader excelReader = EasyExcelFactory.read(file).build();

        excelReader.read(new ReadSheet(0));

        AnalysisContext analysisContext = excelReader
                .analysisContext();

        Integer approximateTotalRowNumber = analysisContext
                .readSheetHolder()
                .getApproximateTotalRowNumber();

        System.err.println(approximateTotalRowNumber);

    }

    @Test
    public void workbook(){
//        Workbook
    }
}
