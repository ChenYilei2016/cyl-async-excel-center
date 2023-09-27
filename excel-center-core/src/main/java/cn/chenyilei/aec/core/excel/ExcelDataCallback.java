package cn.chenyilei.aec.core.excel;

import com.alibaba.excel.context.AnalysisContext;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/27 16:23
 */
@FunctionalInterface
public interface ExcelDataCallback {

    void run(List<Map<Integer, Object>> cachedDataList, AnalysisContext analysisContext, AecPageReadListenerContext context);
}
