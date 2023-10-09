package cn.chenyilei.aec.core.excel;

import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/28 16:33
 */
public class AecExcelWriter {

    private ColumnHeaders columnHeaders;

    private Map<Integer, WriteSheet> writeSheetMap;

    private ExcelWriter easyExcelWriter;

    private Map<String, Integer> sheetNameNoMap;

    public AecExcelWriter(ColumnHeaders columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    /**
     * TODO: simple export
     */
    public AecExcelWriter(List<String> headerNames) {
        this.columnHeaders = columnHeaders;
    }

}
