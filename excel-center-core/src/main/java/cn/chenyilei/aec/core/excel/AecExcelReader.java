package cn.chenyilei.aec.core.excel;

import cn.chenyilei.aec.common.constants.AecConstant;
import cn.chenyilei.aec.common.utils.AssertUtil;
import cn.chenyilei.aec.common.utils.ExcelTypeUtil;
import cn.chenyilei.aec.core.model.core.ColumnHeader;
import cn.chenyilei.aec.core.model.core.ColumnHeaders;
import cn.chenyilei.aec.core.model.core.impl.ColumnHeadersImpl;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2023/10/07 15:16
 */
public class AecExcelReader {
    /**
     * 额外自定义表头
     */
    @Getter
    private final ColumnHeaders columnHeaders;

    @Getter
    private final AecExcelReaderContext aecExcelReaderContext;

    public AecExcelReader(ColumnHeaders columnHeaders, AecExcelReaderContext aecExcelReaderContext) {
        this.columnHeaders = columnHeaders;
        this.aecExcelReaderContext = aecExcelReaderContext;

        preCheckContextValid(aecExcelReaderContext);
    }

    private void preCheckContextValid(AecExcelReaderContext aecExcelReaderContext) {
        AssertUtil.notNull(aecExcelReaderContext, "no aecExcelReaderContext");
        AssertUtil.notNull(aecExcelReaderContext.getExcelDataReaderCallback(), "no getExcelDataReaderCallback");
    }


    public void readParse(InputStream inputStream) {
        ExcelTypeEnum excelTypeEnum = ExcelTypeUtil.recognitionExcelType(inputStream);
        ExcelReader excelReader = EasyExcel.read(inputStream).excelType(excelTypeEnum).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();

        List<ReadSheet> sheetsNeedReads = new ArrayList<>();
        for (ReadSheet readSheet : readSheets) {
            String sheetName = readSheet.getSheetName();

            if (CollectionUtils.isNotEmpty(getAecExcelReaderContext().getReadSheetNames())
                    && !getAecExcelReaderContext().getReadSheetNames().contains(sheetName)) {
                //过滤不需要的sheet
                continue;
            }

            AecPageReadListener aecPageReadListener = new AecPageReadListener(aecExcelReaderContext.getBatchCount());

            readSheet.setCustomReadListenerList(Lists.newArrayList(aecPageReadListener));
            readSheet.setHeadRowNumber(columnHeaders.getHeaderRowCount(readSheet.getSheetNo()));
            /**
             * 这里会回调 {@link AecExcelReaderContext#getExcelDataReaderCallback()}
             */
            excelReader.read(readSheet);
            sheetsNeedReads.add(readSheet);
        }
    }


    private class AecPageReadListener extends AnalysisEventListener<Map<Integer, Object>> implements ReadListener<Map<Integer, Object>> {
        /**
         * Temporary storage of data
         */
        private List<DataGroup.Item> cachedDataList = null;

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

        public AecPageReadListener() {
            this(200);
        }

        public AecPageReadListener(Integer batchCount) {
            this.batchCount = batchCount == null ? 200 : batchCount;
            this.cachedDataList = ListUtils.newArrayListWithExpectedSize(this.batchCount);
        }

        @Override
        public void invoke(Map<Integer, Object> data, AnalysisContext context) {
            Map<String, Object> line = new HashMap<>(data.size() * 2);

            if (columnHeaders != ColumnHeaders.EMPTY) {
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

            //这里添加的是 下标:value
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
            item.setValues(line);

            this.cachedDataList.add(item);

            if (cachedDataList.size() >= batchCount) {
                aecExcelReaderContext.readCallback(cachedDataList, this.uploadData, this.uploadHeaderNameKeys, context, aecExcelReaderContext);
                cachedDataList = ListUtils.newArrayListWithExpectedSize(batchCount);
            }
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            if (CollectionUtils.isNotEmpty(cachedDataList)) {
                aecExcelReaderContext.readCallback(cachedDataList, this.uploadData, this.uploadHeaderNameKeys, context, aecExcelReaderContext);
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
//            this.uploadData.setItems(new ArrayList<>()); // import is no
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
}
