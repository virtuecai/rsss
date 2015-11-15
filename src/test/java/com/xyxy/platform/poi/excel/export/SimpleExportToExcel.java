package com.xyxy.platform.poi.excel.export;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/7 0007.
 */
public class SimpleExportToExcel {

    public static void main2(String[] args) {

        try {
            //创建新的Excel工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            //如果新建一个名为“sheet1”的工作表
            Sheet sheet = workbook.createSheet("sheet1");
//          Sheet sheet=workbook.createSheet();


            //在索引0的位置创建行（最顶端的行）
            Row row = sheet.createRow(0);
            //在索引0的位置创建单元格(左上端)
            Cell cell = row.createCell(0);
            //定义单元格为字符串类型
            cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
            //在单元格中输入一些内容
            cell.setCellValue(123456);

            //新建文件输出流
            FileOutputStream fOut = new FileOutputStream("c:\\bookdata.xlsx");
            //将数据写入Excel
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<User,List<User2>> map = new LinkedHashMap();
        User user = new User(1l, "user 1l");
        List<User2> user2List = new ArrayList<>();
        user2List.add(new User2(1l,"user2 1l"));
        user2List.add(new User2(2l,"user2 2l"));
        map.put(user, user2List);

        user = new User(2l, "user 2l");
        user2List = new ArrayList<>();
        user2List.add(new User2(3l,"user2 3l"));
        user2List.add(new User2(4l, "user2 4l"));
        map.put(user, user2List);

        System.out.println(map);
    }



}

class User {
    private Long id;
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class User2 {
    private Long id;
    private String name;

    public User2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
