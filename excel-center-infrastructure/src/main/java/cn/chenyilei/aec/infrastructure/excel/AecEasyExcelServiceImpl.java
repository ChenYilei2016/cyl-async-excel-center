package cn.chenyilei.aec.infrastructure.excel;

import cn.chenyilei.aec.common.utils.ExcelTypeUtil;
import cn.chenyilei.aec.core.excel.AecPageReadListener;
import cn.chenyilei.aec.core.excel.AecPageReadListenerContext;
import cn.chenyilei.aec.domain.excel.AecExcelService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyilei
 * @date 2023/09/26 16:47
 */
@Slf4j
public class AecEasyExcelServiceImpl implements AecExcelService {

    /**
     * @formatter:off
     *      FileInputStream fis = null;
     *         try {
     *             if (ExcelTypeEnum.CSV.equals(excelTypeEnum)) {
     *                 CsvImportParams csvImportParams = new CsvImportParams();
     *                 fis = new FileInputStream(excelFile);
     *                 csvImportParams.setEncoding(encoding);
     *                 CsvImportUtil.importCsv(fis, Map.class, csvImportParams, saveDataHandler);
     *             } else if (ExcelTypeEnum.XSSF.equals(excelTypeEnum)) {
     *                 //TODO 优化Excel
     *                 fis = new FileInputStream(excelFile);
     *                 ImportParams params = new ImportParams();
     *                 ExcelImportUtil.importExcelBySax(fis, Map.class, params, saveDataHandler);
     *                 //ExcelReader.readDataFromExcelAndValidateMeta(fis, ExcelType.XSSF,saveDataHandler);
     *             }
     *         } catch (Exception e) {
     *             throw new CommonException(CommonErrorCode.VALIDATE_ERROR, "请检查excel版本或格式不正确,请下载模板", e);
     *         } finally {
     *             IOUtils.closeQuietly(fis);
     *             if (ExcelTypeEnum.CSV.equals(excelTypeEnum)) {
     *                 CsvExportUtil.closeExportBigExcel();
     *             }
     *             if (ExcelTypeEnum.XSSF.equals(excelTypeEnum)) {
     *                 ExcelExportUtil.closeExportBigExcel();
     *             }
     *         }
     * @formatter:on
     * @param inputStream
     */
    @Override
    public void readParse(InputStream inputStream, AecPageReadListenerContext readListenerContext) {
        ExcelTypeEnum excelTypeEnum = ExcelTypeUtil.recognitionExcelType(inputStream);
        ExcelReader excelReader = EasyExcel.read(inputStream).excelType(excelTypeEnum).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();

        int sheetIndex = 0;
        List<ReadSheet> sheetsNeedReads = new ArrayList<>();
        for (ReadSheet readSheet : readSheets) {
            String sheetName = readSheet.getSheetName();
            if (sheetName != null && sheetName.startsWith("hidden_")) {
                log.warn("ignore sheet, main:{}, sheetNo:{}, sheetName:{}", null, readSheet.getSheetNo(), sheetName);
                continue;
            }

            AecPageReadListener aecPageReadListener = new AecPageReadListener(readListenerContext);
            readSheet.setCustomReadListenerList(Lists.newArrayList(aecPageReadListener));
            excelReader.read(readSheet);
            sheetIndex++;
            sheetsNeedReads.add(readSheet);
        }
        excelReader.read(sheetsNeedReads);
    }

}
