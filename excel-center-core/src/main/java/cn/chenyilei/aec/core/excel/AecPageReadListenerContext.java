package cn.chenyilei.aec.core.excel;

import com.alibaba.excel.context.AnalysisContext;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/27 14:35
 */
public class AecPageReadListenerContext {


    private ExcelDataCallback callback;

    public AecPageReadListenerContext(ExcelDataCallback callback) {
        this.callback = callback;
    }

    @Getter
    @Setter
    private int batchCount = 200;

    public void doBatchDataWithBizCallback(List<Map<Integer, Object>> cachedDataList, AnalysisContext context) {
        callback.run(cachedDataList, context, this);
    }
}
