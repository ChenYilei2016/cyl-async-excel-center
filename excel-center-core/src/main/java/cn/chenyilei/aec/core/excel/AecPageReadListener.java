package cn.chenyilei.aec.core.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/09/27 14:25
 * @see com.alibaba.excel.read.listener.PageReadListener
 */
public class AecPageReadListener extends AnalysisEventListener<Map<Integer, Object>> implements ReadListener<Map<Integer, Object>> {
    /**
     * Default single handle the amount of data
     */
    private final AecPageReadListenerContext aecPageReadListenerContext;
    /**
     * Temporary storage of data
     */
    private List<Map<Integer, Object>> cachedDataList = null;

    /**
     * Single handle the amount of data
     */
    private int batchCount = 100;

    public AecPageReadListener(AecPageReadListenerContext aecPageReadListenerContext) {
        this.aecPageReadListenerContext = aecPageReadListenerContext;
        this.batchCount = aecPageReadListenerContext.getBatchCount();
        this.cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
    }

    @Override
    public void invoke(Map<Integer, Object> data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() >= batchCount) {
            aecPageReadListenerContext.doBatchDataWithBizCallback(cachedDataList, context);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            aecPageReadListenerContext.doBatchDataWithBizCallback(cachedDataList, context);
        }
    }

}
