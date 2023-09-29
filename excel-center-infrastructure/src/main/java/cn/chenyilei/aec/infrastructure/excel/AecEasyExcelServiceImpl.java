package cn.chenyilei.aec.infrastructure.excel;

import cn.chenyilei.aec.common.utils.ExcelTypeUtil;
import cn.chenyilei.aec.core.excel.AecExcelWriter;
import cn.chenyilei.aec.core.excel.AecPageReadListener;
import cn.chenyilei.aec.core.excel.AecPageReadListenerContext;
import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import cn.chenyilei.aec.domain.excel.AecExcelService;
import cn.chenyilei.aec.domain.oss.AecOssService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/26 16:47
 */
@Slf4j
public class AecEasyExcelServiceImpl implements AecExcelService {


    @Resource
    private AecOssService aecOssService;

    @Override
    public void readParse(InputStream inputStream, AecPageReadListenerContext readListenerContext) {
        ExcelTypeEnum excelTypeEnum = ExcelTypeUtil.recognitionExcelType(inputStream);
        ExcelReader excelReader = EasyExcel.read(inputStream).excelType(excelTypeEnum).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();

        List<ReadSheet> sheetsNeedReads = new ArrayList<>();
        for (ReadSheet readSheet : readSheets) {
            String sheetName = readSheet.getSheetName();

            if (!readListenerContext.getReadSheetNames().isEmpty()
                    && !readListenerContext.getReadSheetNames().contains(sheetName)) {
                //过滤不需要的sheet
                continue;
            }

            AecPageReadListener aecPageReadListener = new AecPageReadListener(readListenerContext);
            readSheet.setCustomReadListenerList(Lists.newArrayList(aecPageReadListener));
            readSheet.setHeadRowNumber(readListenerContext.getColumnHeaders().getHeaderRowCount(readSheet.getSheetNo()));
            excelReader.read(readSheet);
            sheetsNeedReads.add(readSheet);
        }
        /**
         * 这里会回调{@link AecPageReadListenerContext#doBatchDataWithBizCallback(List, cn.chenyilei.aec.core.excel.DataGroup.Data, java.util.Map, AnalysisContext)}
         */
        excelReader.read(sheetsNeedReads);
    }

    /**
     * build 空excel
     */
    public AecExcelWriter buildAecExcelWriter(String fileName) {
//        return new AecExcelWriter();
        return null;
    }

    public InputStream write() {
        //导出 ,   头信息?  client传入

//        String sheetName, ColumnHeaders columnHeaders, List<Map<String, Object>> data

//        aecOssService.upload(,,);
        return null;
    }

}
