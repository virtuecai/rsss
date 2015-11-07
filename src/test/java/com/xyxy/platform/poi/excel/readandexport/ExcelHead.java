package com.xyxy.platform.poi.excel.readandexport;


import java.util.List;
import java.util.Map;

/**
 * Excel头信息
 *
 * @createTime: 2012-4-18 下午01:17:53
 * @author: <a href="mailto:hubo@feinno.com">hubo</a>
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime:
 * @updateAuthor: <a href="mailto:hubo@feinno.com">hubo</a>
 * @changesSum:
 */
public class ExcelHead {
    /**
     * 列信息
     */
    private List<ExcelColumn> columns;

    /**
     * 需要转换的列
     */
    private Map<String, Map> columnsConvertMap;

    /**
     * 头部所占用的行数
     */
    private int rowCount;

    /**
     * 头部所占用的列数
     */
    private int columnCount;

    public List<ExcelColumn> getColumns() {
        return columns;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumns(List<ExcelColumn> columns) {
        this.columns = columns;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public Map<String, Map> getColumnsConvertMap() {
        return columnsConvertMap;
    }

    public void setColumnsConvertMap(Map<String, Map> columnsConvertMap) {
        this.columnsConvertMap = columnsConvertMap;
    }

    @Override
    public String toString() {
        return "ExcelHead [columnCount=" + columnCount + ", columns=" + columns
                + ", columnsConvertMap=" + columnsConvertMap + ", rowCount="
                + rowCount + "]";
    }

}