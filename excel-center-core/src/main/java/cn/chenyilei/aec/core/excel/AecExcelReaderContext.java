package cn.chenyilei.aec.core.excel;

import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import com.alibaba.excel.context.AnalysisContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenyilei
 * @date 2023/10/07 16:16
 */
public class AecExcelReaderContext {

    /**
     * 需要读取的sheet名称, 默认全部
     */
    @Setter
    @Getter
    private Set<String> readSheetNames = null;

    /**
     * 每次处理的excel条数
     */
    @Setter
    @Getter
    private int batchCount = 200;

    /**
     * 读取到batch条数据后的回调 callback
     */
    @Getter
    private final ExcelDataReaderCallback excelDataReaderCallback;


    public AecExcelReaderContext(ExcelDataReaderCallback excelDataReaderCallback) {
        this.excelDataReaderCallback = excelDataReaderCallback;
    }

    public void readCallback(List<DataGroup.Item> excelDataList, DataGroup.Data metaData, Map<Integer, List<String>> uploadHeaderNameKeys, AnalysisContext analysisContext, AecExcelReaderContext aecReaderContext) {
        this.excelDataReaderCallback.run(excelDataList, metaData, uploadHeaderNameKeys, analysisContext, aecReaderContext);
    }

}
