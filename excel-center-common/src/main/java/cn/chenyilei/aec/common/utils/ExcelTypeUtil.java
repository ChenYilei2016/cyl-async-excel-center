package cn.chenyilei.aec.common.utils;

import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.SneakyThrows;
import org.apache.poi.util.IOUtils;

import java.io.InputStream;

import static com.alibaba.excel.support.ExcelTypeEnum.XLSX;

/**
 * @author chenyilei
 * @date 2023/09/27 11:38
 */
public class ExcelTypeUtil {

    @SneakyThrows
    public static ExcelTypeEnum recognitionExcelType(InputStream inputStream) {
        // Grab the first bytes of this stream
        byte[] data = IOUtils.peekFirstNBytes(inputStream, 8);
        if (findMagic(XLSX.getMagic(), data)) {
            return XLSX;
        } else if (findMagic(ExcelTypeEnum.XLS.getMagic(), data)) {
            return ExcelTypeEnum.XLS;
        }
        // csv has no fixed prefix, if the format is not specified, it defaults to csv
        return ExcelTypeEnum.CSV;
    }

    public static boolean findMagic(byte[] expected, byte[] actual) {
        int i = 0;
        for (byte expectedByte : expected) {
            if (actual[i++] != expectedByte && expectedByte != '?') {
                return false;
            }
        }
        return true;
    }
}
