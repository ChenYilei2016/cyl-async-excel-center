package cn.chenyilei.aec.core.excel;

import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chenyilei
 * @date 2023/09/27 14:35
 */
public class AecPageReadListenerContext {

    @Getter
    private ColumnHeaders columnHeaders;

    @Getter
    private Set<String> readSheetNames = Sets.newHashSet();

    private ExcelDataCallback callback;


    public AecPageReadListenerContext(ColumnHeaders columnHeaders, ExcelDataCallback callback) {
        this.callback = callback;
        this.columnHeaders = columnHeaders;

        assert callback != null;
    }

    public void doBatchDataWithBizCallback(List<DataGroup.Item> cachedDataList, DataGroup.Data uploadData, Map<Integer, List<String>> uploadHeaderNameKeys, AnalysisContext context) {
        callback.run(cachedDataList, uploadData, uploadHeaderNameKeys, context, this);
    }
}
