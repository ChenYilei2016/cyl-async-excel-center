package cn.chenyilei.center.sample.easyexcel.read;

import cn.chenyilei.aec.common.utils.ResourceLoadUtil;
import cn.chenyilei.aec.core.excel.AecExcelReader;
import cn.chenyilei.aec.core.excel.AecExcelReaderContext;
import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import cn.chenyilei.aec.core.model.core.impl.ColumnHeadersImpl;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

/**
 * @author chenyilei
 * @date 2023/09/27 14:44
 */
public class TestPageReadExcel {

    /**
     * [{0=head1Value1, 1=head2Value1, 2=head2Value1}, {0=head1Value2, 1=head2Value2, 2=head2Value2}, {0=head1Value3, 1=head2Value3, 2=head2Value3}, {0=head1Value4, 1=head2Value4, 2=head2Value4}, {0=head1Value5, 1=head2Value5, 2=head2Value5}]
     * [{0=head1Value6, 1=head2Value6, 2=head2Value6}, {0=head1Value7, 1=head2Value7, 2=head2Value7}, {0=head1Value8, 1=head2Value8, 2=head2Value8}, {0=head1Value9, 1=head2Value9, 2=head2Value9}, {0=head1Value10, 1=head2Value10, 2=head2Value10}]
     * [{0=head1Value11, 1=head2Value11, 2=head2Value11}, {0=head1Value12, 1=head2Value12}]
     */
    @Test
    public void test1() {

        //page 5
        AecExcelReaderContext aecExcelReaderContext = new AecExcelReaderContext((excelDataList, metaData, uploadHeaderNameKeys, analysisContext, aecReaderContext) -> {
            System.err.println(excelDataList);
        });
        aecExcelReaderContext.setBatchCount(5);
//        aecExcelReaderContext.setReadSheetNames(Sets.newHashSet("Sheet1"));

        AecExcelReader aecExcelReader = new AecExcelReader(ColumnHeaders.EMPTY, aecExcelReaderContext);

        aecExcelReader.readParse(ResourceLoadUtil.readFileStream("classpath:excel/demoExcel.xlsx"));

    }
}
