package cn.chenyilei.aec.core.excel;

import cn.chenyilei.aec.common.constants.AecConstant;
import cn.chenyilei.aec.core.model.core.ColumnHeader;
import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import cn.chenyilei.aec.core.model.core.impl.ColumnHeadersImpl;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<DataGroup.Item> cachedDataList = null;

    /**
     * 额外自定义表头
     */
    private final ColumnHeaders columnHeaders;

    /**
     * excel有的表头
     */
    private final Map<Integer, List<String>> uploadHeaderNameKeys = new HashMap<>(8);

    /**
     * 上传的一些元数据
     */
    private DataGroup.Data uploadData;

    /**
     * Single handle the amount of data
     */
    private int batchCount = 200;

    public AecPageReadListener(AecPageReadListenerContext aecPageReadListenerContext) {
        this(200, aecPageReadListenerContext);
    }

    public AecPageReadListener(Integer batchCount, AecPageReadListenerContext aecPageReadListenerContext) {
        this.aecPageReadListenerContext = aecPageReadListenerContext;
        this.batchCount = batchCount == null ? 200 : batchCount;
        this.cachedDataList = ListUtils.newArrayListWithExpectedSize(this.batchCount);
        this.columnHeaders = aecPageReadListenerContext.getColumnHeaders();
    }

    @Override
    public void invoke(Map<Integer, Object> data, AnalysisContext context) {
        Map<String, Object> line = new HashMap<>(data.size() * 2);

        if (columnHeaders != ColumnHeadersImpl.EMPTY) {
            //扩展自定义header处理
            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                Integer column = entry.getKey();
                List<String> headers = uploadHeaderNameKeys.get(column);
                ColumnHeader columnHeader = columnHeaders.getColumnHeaderByHeaderName(headers);
                if (columnHeader != null) {
                    String fieldName = columnHeader.getFieldName();
                    if (columnHeader.getDynamicColumn()) {
                        Object o = line.get(fieldName);
                        if (o == null) {
                            o = new HashMap<>();
                            line.put(fieldName, o);
                        } else {
                            if (o instanceof Map) {
                                Map map = (Map) o;
                                map.put(columnHeader.getDynamicColumnKey(), entry.getValue());
                            }
                        }
                    } else {
                        line.put(fieldName, entry.getValue());
                    }
                }
            }
        } else {
            //没有外部额外信息的header处理
            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                Integer column = entry.getKey();
                List<String> headers = uploadHeaderNameKeys.get(column);
                String firstHeader = IterableUtils.first(headers);

                if (firstHeader != null) {
                    line.put(firstHeader, entry.getValue());
                }
            }
        }

        for (Map.Entry<Integer, Object> entry : data.entrySet()) {
            line.put(entry.getKey().toString(), entry.getValue());
        }

        DataGroup.Item item = new DataGroup.Item();
        String sheetName = context.readSheetHolder().getSheetName();
        String sheetNo = context.readSheetHolder().getSheetNo().toString();
        Integer rowIndex = context.readRowHolder().getRowIndex();
        String groupName = sheetName + "@" + sheetNo;
        String code = groupName + "@" + rowIndex;
        item.setCode(code);
        item.setSheetName(sheetName);
        item.setValues(line);

        this.cachedDataList.add(item);

        if (cachedDataList.size() >= batchCount) {
            aecPageReadListenerContext.doBatchDataWithBizCallback(cachedDataList, this.uploadData, this.uploadHeaderNameKeys, context);
            cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (CollectionUtils.isNotEmpty(cachedDataList)) {
            aecPageReadListenerContext.doBatchDataWithBizCallback(cachedDataList, this.uploadData, this.uploadHeaderNameKeys, context);
        }
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (this.uploadData == null) {
            String sheetName = context.readSheetHolder().getSheetName();
            String sheetNo = context.readSheetHolder().getSheetNo().toString();
            String groupName = sheetName + "@" + sheetNo;
            this.uploadData = new DataGroup.Data();
            this.uploadData.setCode(groupName);
//            this.uploadData.setItems(new ArrayList<>());
            Map<String, String> meta = new HashMap<>();
            meta.put(AecConstant.sheetNoKey, sheetNo);
            meta.put(AecConstant.sheetNameKey, sheetName);
            this.uploadData.setMeta(meta);
        }

        for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
            Integer key = entry.getKey();
            List<String> headers = uploadHeaderNameKeys.get(key);
            if (headers == null) {
                headers = new ArrayList<>();
                headers.add(entry.getValue());
            } else {
                headers.add(entry.getValue());
            }
            uploadHeaderNameKeys.put(key, headers);
        }
    }
}
