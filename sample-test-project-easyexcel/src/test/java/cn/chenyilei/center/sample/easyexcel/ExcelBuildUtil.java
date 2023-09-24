package cn.chenyilei.center.sample.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;
import java.util.List;

/**
 * @author chenyilei
 * @date 2023/09/22 14:27
 */
public class ExcelBuildUtil {

    @Test
    public void testBuild(){
        FileSystemResource fileSystemResource = new FileSystemResource("easyexcel/test1.xlsx");
        if (!fileSystemResource.exists()) {

        }
        EasyExcel.write(fileSystemResource.getFile()).head(head()).sheet("模板").doWrite(dataList());
    }

    /**
     * csv
     */

    /**
     * xlsx
     */

    /**
     * xls
     */

    private List<List<String>> head() {
        List<List<String>> list = Lists.newArrayList();

        List<String> head0 = Lists.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());

        List<String> head1 = Lists.newArrayList();
        head1.add("数字" + System.currentTimeMillis());

        List<String> head2 = Lists.newArrayList();
        head2.add("日期" + System.currentTimeMillis());

        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Object> data = Lists.newArrayList();
            data.add("字符串" + i);
            data.add(0.56);
            data.add(new Date());
            list.add(data);
        }
        return list;
    }
}
