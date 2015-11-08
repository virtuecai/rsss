/*******************************************************************************
 * Copyright (c) 2005, 2014
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.xyxy.platform.web.product;

import com.alibaba.fastjson.JSONObject;
import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.excel.AgentSalesDetail;
import com.xyxy.platform.excel.ExcelColumn;
import com.xyxy.platform.excel.ExcelHead;
import com.xyxy.platform.excel.ExcelHelper;
import com.xyxy.platform.modules.core.web.bind.annotation.FormModel;
import com.xyxy.platform.service.ProductService;
import com.xyxy.platform.service.account.AccountService;
import com.xyxy.platform.service.account.ShiroDbRealm;
import com.xyxy.platform.service.account.ShiroDbRealm.ShiroUser;
import com.xyxy.platform.web.utils.I18nMessageUtil;
import jodd.datetime.JDateTime;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品controller.
 *
 * caizhengda
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String listView(Model model) {
        model.addAttribute("productList", productService.findAll());
        return "product/list";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(String code, String nameJa, String nameZh, Model model) {
        model.addAttribute("productList", productService.findByConditions(code, nameJa, nameZh));
        model.addAttribute("code", code);
        model.addAttribute("nameZh", nameZh);
        model.addAttribute("model", model);
        return "product/list";
    }

    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody ProductsForm productsForm) {
        ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        //productService.save(productsForm.getProduct1List(), shiroUser.id);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "pathtest", method = RequestMethod.GET)
    public String pathtest(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("WEB-INF/excel/");
        return path;
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(String code, String nameJa, String nameZh, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("productList", productService.findByConditions(code, nameJa, nameZh));
        model.addAttribute("code", code);
        model.addAttribute("nameZh", nameZh);
        model.addAttribute("model", model);

        List<AgentSalesDetail> agentSalesDetails = new ArrayList<AgentSalesDetail>();
        for (int i = 0; i < 20; i++) {
            AgentSalesDetail asd = new AgentSalesDetail();
            asd.setId(i);
            asd.setOrderDate(new JDateTime(2012, 04, 04).convertToDate());
            asd.setBelongOrgName("杭州办" + i);
            asd.setAgentName("杭州萧聘网络\n科技有限公司" + i);
            asd.setEntName("杭州悦奥体育用品销售有限公司" + i);
            asd.setBusinessSum(900);
            asd.setContractSum(900);
            asd.setDividePercent(55);
            asd.setDivideSum(495);
            asd.setOrderType(1);
            asd.setPayforSum(495);
            asd.setPayforDate(new JDateTime(2012, 04, 04).convertToDate());
            asd.setIsReceive(1);
            asd.setReceiveSum(495);
            asd.setReceiveDate(new JDateTime(2012, 04, 04).convertToDate());
            asd.setRemark("remark1");

            agentSalesDetails.add(asd);
        }

        // excel结构
        List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
        excelColumns.add(new ExcelColumn(0, "id", "序号"));
        excelColumns.add(new ExcelColumn(1, "orderDate", "日期", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(2, "belongOrgName", "分支机构", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(3, "agentName", "代理商名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(4, "entName", "企业名称", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(5, "businessSum", "业务金额", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(6, "contractSum", "合同金额", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(7, "dividePercent", "分成比例", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(8, "divideSum", "分成金额", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(9, "orderType", "订单类型", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(10, "payforSum", "应结算金额（元）", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(11, "payforDate", "应结算日期", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(12, "isReceive", "是否到帐", Cell.CELL_TYPE_STRING));
        excelColumns.add(new ExcelColumn(13, "receiveSum", "到帐金额（元）", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(14, "receiveDate", "到帐日期", Cell.CELL_TYPE_NUMERIC));
        excelColumns.add(new ExcelColumn(15, "remark", "备注", Cell.CELL_TYPE_STRING));

        // 需要特殊转换的单元
        Map<String, Map> excelHeadConvertMap = new HashMap<>();
        Map isReceive = new HashMap();
        isReceive.put(1, "是");
        isReceive.put(0, "否");
        excelHeadConvertMap.put("isReceive", isReceive);
        Map orderType = new HashMap();
        orderType.put(1, "新订单");
        orderType.put(2, "续订订单");
        excelHeadConvertMap.put("orderType", orderType);

        String exceFileBasePath = request.getSession().getServletContext().getRealPath("WEB-INF/classes/excel");
        File modelFile = new File(exceFileBasePath + File.separator + "2010_model.xlsx");
        File outputFile = new File(exceFileBasePath + File.separator + "2010_export.xlsx");

        ExcelHead head = new ExcelHead();
        head.setRowCount(1); // 模板中头部所占行数
        head.setColumns(excelColumns);  // 列的定义
        head.setColumnsConvertMap(excelHeadConvertMap); // 列的转换

        ExcelHelper.getInstanse().exportExcelFile(head, modelFile, outputFile, agentSalesDetails);


        //--------------------------------------------------------------------------------------------
       try {
           String name = URLEncoder.encode(outputFile.getName(), "UTF-8");
           response.setHeader("Content-disposition", "attachment;filename=" + name);
           OutputStream os = response.getOutputStream();
           IOUtils.copy(new FileInputStream(outputFile), os);
           os.flush();
           os.close();
           outputFile.delete();
       } catch (Exception e) {
           System.out.println(e);
       } finally {
           outputFile.delete();
       }

    }
}
