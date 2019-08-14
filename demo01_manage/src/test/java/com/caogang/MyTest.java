package com.caogang;

import com.caogang.dao.MenuDao;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: xiaogang
 * @date: 2019/8/13 - 14:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    private MenuDao menuDao;

    @Test
    public void test001() {

        XSSFWorkbook sheets = new XSSFWorkbook();

        XSSFSheet sheet = sheets.createSheet("九九乘法表");

        for (int i=1;i<=9;i++){

            XSSFRow row = sheet.createRow(i - 1);

            for (int j = 1; j<= 9; j++) {

                XSSFCell cell = row.createCell(j - 1);

                cell.setCellValue(i+"*"+j+"="+i*j);

            }

        }

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("F:\\test.xlsx");

            try {

                sheets.write(fileOutputStream);

            } catch (IOException e) {

                e.printStackTrace();

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }
    }

}
