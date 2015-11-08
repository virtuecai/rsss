package com.xyxy.platform.excel;


import jodd.bean.BeanUtil;
import jodd.datetime.JDateTime;
import jodd.util.StringUtil;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * Excel助手类
 *
 * @createTime: 2012-4-19 上午10:14:46
 * @author: <a href="mailto:hubo@feinno.com">hubo</a>
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime:
 * @updateAuthor: <a href="mailto:hubo@feinno.com">hubo</a>
 * @changesSum:
 *
 */
public class ExcelHelper {
    private static ExcelHelper helper = null;

    private ExcelHelper() {

    }

    public static synchronized ExcelHelper getInstanse() {
        if(helper == null) {
            helper = new ExcelHelper();
        }
        return helper;
    }

    /**
     * 将Excel文件导入到list对象
     * @param head  文件头信息
     * @param file  导入的数据源
     * @param cls   保存当前数据的对象
     * @return
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return List
     * 2012-4-19 下午01:17:48
     */
    public List importToObjectList(ExcelHead head, File file, Class cls) {
        List contents = null;
        FileInputStream fis;
        // 根据excel生成list类型的数据
        List<List> rows;
        try {
            fis = new FileInputStream(file);
            rows = excelFileConvertToList(fis);

            // 删除头信息
            for (int i = 0; i < head.getRowCount(); i++) {
                rows.remove(0);
            }

            // 将表结构转换成Map
            Map<Integer, String> excelHeadMap = convertExcelHeadToMap(head.getColumns());
            // 构建为对象
            contents = buildDataObject(excelHeadMap, head.getColumnsConvertMap(), rows, cls);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contents;
    }

    /**
     * 导出数据至Excel文件
     * @param head  报表头信息
     * @param modelFile  模板Excel文件
     * @param outputFile 导出文件
     * @param dataList  导入excel报表的数据来源
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return void
     * 2012-4-19 上午10:04:30
     */
    public void exportExcelFile(ExcelHead head, File modelFile, File outputFile, List<?> dataList) {
        // 读取导出excel模板
        InputStream inp = null;
        Workbook wb = null;
        try {
            inp = new FileInputStream(modelFile);
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            // 生成导出数据
            buildExcelData(sheet, head, dataList);

            // 导出到文件中
            FileOutputStream fileOut = new FileOutputStream(outputFile);
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将报表结构转换成Map
     * @param excelColumns
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return void
     * 2012-4-18 下午01:31:12
     */
    private Map<Integer, String> convertExcelHeadToMap(List<ExcelColumn> excelColumns) {
        Map<Integer, String> excelHeadMap = new HashMap<Integer, String>();
        for (ExcelColumn excelColumn : excelColumns) {
            if(StringUtil.isEmpty(excelColumn.getFieldName())) {
                continue;
            } else {
                excelHeadMap.put(excelColumn.getIndex(), excelColumn.getFieldName());
            }
        }
        return excelHeadMap;
    }

    /**
     * 生成导出至Excel文件的数据
     * @param sheet 工作区间
     * @param head  excel表头
     * @param excelHeadMap  excel表头对应实体属性
     * @param dataList      导入excel报表的数据来源
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return void
     * 2012-4-19 上午09:36:37
     */
    private void buildExcelData(Sheet sheet, ExcelHead head, List<?> dataList) {
        List<ExcelColumn> excelColumns = head.getColumns();
        Map<String, Map> excelHeadConvertMap = head.getColumnsConvertMap();

        // 将表结构转换成Map
        Map<Integer, String> excelHeadMap = convertExcelHeadToMap(excelColumns);

        // 从第几行开始插入数据
        int startRow = head.getRowCount();
        int order = 1;
        for (Object obj : dataList) {
            Row row = sheet.createRow(startRow++);
            for (int j = 0; j < excelColumns.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellType(excelColumns.get(j).getType());
                String fieldName = excelHeadMap.get(j);
                if(fieldName != null) {
                    Object valueObject = BeanUtil.getProperty(obj, fieldName);

                    // 如果存在需要转换的字段信息，则进行转换
                    if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
                        valueObject = excelHeadConvertMap.get(fieldName).get(valueObject);
                    }

                    if(valueObject == null) {
                        cell.setCellValue("");
                    } else if (valueObject instanceof Integer) {
                        cell.setCellValue((Integer)valueObject);
                    } else if (valueObject instanceof String) {
                        cell.setCellValue((String)valueObject);
                    } else if (valueObject instanceof Date) {
                        cell.setCellValue(new JDateTime((Date)valueObject).toString("YYYY-MM-DD"));
                    } else {
                        cell.setCellValue(valueObject.toString());
                    }
                } else {
                    cell.setCellValue(order++);
                }
            }
        }
    }

    /**
     * 将Excel文件内容转换为List对象
     * @param fis   excel文件
     * @return  List<List> list存放形式的内容
     * @throws IOException
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return List<List>
     * 2012-4-18 上午11:37:17
     */
    public List<List> excelFileConvertToList(FileInputStream fis) throws Exception {
        Workbook wb = WorkbookFactory.create(fis);

        Sheet sheet = wb.getSheetAt(0);

        List<List> rows = new ArrayList<List>();
        for (Row row : sheet) {
            List<Object> cells = new ArrayList<Object>();
            for (Cell cell : row) {
                Object obj = null;

                CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        obj = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            obj = new JDateTime(cell.getDateCellValue());
                        } else {
                            obj = cell.getNumericCellValue();
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        obj = cell.getBooleanCellValue();
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        obj = cell.getNumericCellValue();
                        break;
                    default:
                        obj = null;
                }
                cells.add(obj);
            }
            rows.add(cells);
        }
        return rows;
    }

    /**
     * 根据Excel生成数据对象
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap 需要特殊转换的单元
     * @param rows
     * @param cls
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return void
     * 2012-4-18 上午11:39:43
     */
    private List buildDataObject(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List<List> rows, Class cls) {
        List contents = new ArrayList();
        for (List list : rows) {
            // 如果当前第一列中无数据,则忽略当前行的数据
            if(list == null || list.get(0) == null) {
                break;
            }
            // 当前行的数据放入map中,生成<fieldName, value>的形式
            Map<String, Object> rowMap = rowListToMap(excelHeadMap, excelHeadConvertMap, list);

            // 将当前行转换成对应的对象
            Object obj = null;
            try {
                obj = cls.newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            BeanUtil.populateBean(obj, rowMap);

            contents.add(obj);
        }
        return contents;
    }

    /**
     * 将行转行成map,生成<fieldName, value>的形式
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap excelHeadConvertMap
     * @param list
     * @return
     * @auther <a href="mailto:hubo@feinno.com">hubo</a>
     * @return Map<String,Object>
     * 2012-4-18 下午01:48:41
     */
    private Map<String, Object> rowListToMap(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List list) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        for(int i = 0; i < list.size(); i++) {
            String fieldName =  excelHeadMap.get(i);
            // 存在所定义的列
            if(fieldName != null) {
                Object value = list.get(i);
                if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
                    value = excelHeadConvertMap.get(fieldName).get(value);
                }
                rowMap.put(fieldName, value);
            }
        }
        return rowMap;
    }
}