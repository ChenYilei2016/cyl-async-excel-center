package cn.chenyilei.center.sample.easyexcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TestWriteExcel {

    @Test
    public void writeSheetTest() throws IOException {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();

        File file = new File("src/main/resources/excel/writeExcel.xlsx");
        System.err.println(file.getAbsolutePath());

        ExcelWriter excelWriter = EasyExcel.write(fastByteArrayOutputStream).build();

        for (int i = 0; i < 2; i++) {
            doOneSheet("mySheet_" + i, excelWriter);
        }

        IOUtils.close(excelWriter);

        IOUtils.copy(fastByteArrayOutputStream.getInputStream(), Files.newOutputStream(file.toPath()));

    }

    private void doOneSheet(String sheetName, ExcelWriter excelWriter) {
        List<List<String>> heads = Lists.newArrayList();
        heads.add(Lists.newArrayList("head1"));

        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.writerSheet()
//                .sheetNo(0)
                .sheetName(sheetName)
                .needHead(true)
                .head(heads);

        WriteSheet writeSheet = sheetBuilder.build();
        excelWriter.writeContext().currentSheet(writeSheet, WriteTypeEnum.ADD);


        //假设查询了100次库
        for (int i = 0; i < 30; i++) {
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            //每次1万数据
            excelWriter
                    .write(buildOneSelectDatas(i + "", 10000), writeSheet);
        }

    }

    private List<List<Object>> buildOneSelectDatas(String prefix, int count) {

        List<List<Object>> datas = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            List<Object> oneLine = new ArrayList<>();

            oneLine.add(prefix +"_"+RandomStringUtils.random(6));
            oneLine.add(prefix +"_"+RandomStringUtils.random(6));
            oneLine.add(prefix +"_"+RandomStringUtils.random(6));
            oneLine.add(prefix +"_"+RandomStringUtils.random(6));

            datas.add(oneLine);
        }
        return datas;
    }


}
