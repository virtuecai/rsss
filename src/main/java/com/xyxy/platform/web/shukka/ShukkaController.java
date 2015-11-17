package com.xyxy.platform.web.shukka;

import com.google.common.collect.ImmutableMap;
import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.Product2;
import com.xyxy.platform.entity.ShukkaD;
import com.xyxy.platform.entity.ShukkaH;
import com.xyxy.platform.service.ProductService;
import com.xyxy.platform.service.ShukkaService;
import com.xyxy.platform.service.account.ShiroDbRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * <p/>
 * 真正登录的POST请求由Filter完成,
 */
@Controller
@RequestMapping(value = "/shukka")
public class ShukkaController {

    private static final String uploadBasePath = "upload";
    private static final String fileName = "SHUKKA.HTD";

    @Autowired
    private ShukkaService shukkaService;

    @Autowired
    private ProductService productService;


    @RequestMapping(method = RequestMethod.GET)
    public String emptyListView(Model model) {
        /*List<ShukkaH> shukkaHList = shukkaService.findShukkahAll();
        model.addAttribute("shukkaHList", shukkaHList);*/
        return "shukka/list";
    }

    @RequestMapping(value = "loadHeadData")
    public String loadHeadData(@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        List<ShukkaH> shukkaHList = new ArrayList<>();
        Date date = new Date();
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(file.getInputStream(), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            List<ShukkaD> shukkaDList = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.replace(" ", "");
                if(StringUtils.isNoneBlank(lineTxt)) {
                    String[] split = lineTxt.split(",");
                    if(null != split && split.length == 8) {
                        ShukkaH shukkaH = new ShukkaH();
                        shukkaH.setWarehouseId(Long.parseLong(split[7]));
                        shukkaH.setShukkaDate(new SimpleDateFormat("yyyyMMdd").parse("20" + split[3]));
                        shukkaH.setUserId(Long.parseLong(split[5]));
                        shukkaH.setCustomerId(split[4]);
                        shukkaH.setStatus(0);
                        shukkaH.setCreDate(date);
                        shukkaH.setUpdDate(date);
                        shukkaHList.add(shukkaH);
                    }
                }
            }

            String path = request.getSession().getServletContext().getRealPath("upload");
            File targetFile = new File(path, fileName);
            if(targetFile.exists()){
                targetFile.delete();
            }
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "从文件流总读取文件内容出错");
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        redirectAttributes.addFlashAttribute("message", "读取头信息成功!");
        redirectAttributes.addFlashAttribute("shukkaHList", shukkaHList);
        return "redirect:/shukka";
    }

    /*@RequestMapping(value = "import")
    public String importData(@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes) {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Map<ShukkaH, List<ShukkaD>> importData = new LinkedHashMap<>();
        InputStreamReader read = null;
        try {
            read = new InputStreamReader(file.getInputStream(), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            ShukkaH shukkaH = null;
            List<ShukkaD> shukkaDList = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.replace(" ", "");
                if(StringUtils.isNoneBlank(lineTxt)) {
                    String[] split = lineTxt.split(",");
                    if(null != split && split.length == 8) {
                        //一轮数据结束, 进行下一轮数据组装
                        if(null != shukkaH) {
                            importData.put(shukkaH, shukkaDList);
                        }
                        Date date = new Date();
                        shukkaH = new ShukkaH();
                        shukkaDList = new ArrayList<>();
                        shukkaH.setWarehouseId(Long.parseLong(split[7]));
                        shukkaH.setShukkaDate(new SimpleDateFormat("yyyyMMdd").parse("20" + split[3]));
                        shukkaH.setUserId(Long.parseLong(split[5]));
                        shukkaH.setCustomerId(split[4]);
                        shukkaH.setStatus(0);
                        shukkaH.setCreDate(date);
                        shukkaH.setUpdDate(date);
                        shukkaH.setCreUserId(user.id);
                        shukkaH.setUpdUserId(user.id);
                    } else if(null != split && split.length == 13) {
                        ShukkaD shukkaD = new ShukkaD();
                        String code = split[3];
                        Product1 p1 = productService.findProduct1ByCode(code);
                        Product2 p2 = productService.findProduct2ByCode(code);
                        Integer qty = Integer.parseInt(split[7]);//数量
                        Integer caseQty = Integer.parseInt(split[6]); //箱数
                        shukkaD.setJanCd(code);
                        shukkaD.setCaseQty(caseQty);
                        shukkaD.setQty(qty);
                        shukkaD.setCustomId("");
                        shukkaD.setJanNameJp(null == p1 ? "" : p1.getName());
                        shukkaD.setJanNameCn(null == p2 ? "" : p2.getName());
                        shukkaD.setUnitQty((caseQty == 0 ? 0d : qty / caseQty));
                        //shukkaD.setUnitPrice(0d);// ???? 从哪里取 ?
                        shukkaD.setGrossWeight(null == p2 ? 0d : p2.getGrossWeight());
                        shukkaD.setNetWeight(null == p2 ? 0d : p2.getNetWeight());
                        shukkaD.setMadeinCountry("日本");

                        Date date = new Date();
                        shukkaD.setCreDate(date);
                        shukkaD.setUpdDate(date);
                        shukkaD.setCreUserId(user.id);
                        shukkaD.setUpdUserId(user.id);
                        shukkaDList.add(shukkaD);
                    }
                }
            }
            importData.put(shukkaH, shukkaDList);
            shukkaService.save(importData);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "从文件流总读取文件内容出错");
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("message", "导入数据成功!");
        return "redirect:/shukka";
    }*/


    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(@RequestBody ShukkahsForm shukkahsForm, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        Map<ShukkaH, List<ShukkaD>> importData = new LinkedHashMap<>();
        InputStreamReader read = null;
        try {
            String path = request.getSession().getServletContext().getRealPath(uploadBasePath);
            read = new InputStreamReader(new FileInputStream(new File(path, fileName)), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            ShukkaH shukkaH = null;
            List<ShukkaD> shukkaDList = null;
            ShukkaH shukkaHForm = null;
            int index = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                lineTxt = lineTxt.replace(" ", "");
                if(StringUtils.isNoneBlank(lineTxt)) {
                    String[] split = lineTxt.split(",");
                    if(null != split && split.length == 8) {
                        shukkaHForm = shukkahsForm.getShukkaHList().get(index);//按顺序取出 form 表中的 shukkaH 数据
                        index++;
                        //一轮数据结束, 进行下一轮数据组装
                        if(null != shukkaH) {
                            importData.put(shukkaH, shukkaDList);
                        }
                        Date date = new Date();
                        shukkaH = new ShukkaH();
                        shukkaDList = new ArrayList<>();
                        shukkaH.setWarehouseId(Long.parseLong(split[7]));
                        shukkaH.setShukkaDate(new SimpleDateFormat("yyyyMMdd").parse("20" + split[3]));
                        shukkaH.setUserId(Long.parseLong(split[5]));
                        shukkaH.setCustomerId(split[4]);
                        shukkaH.setStatus(0);
                        shukkaH.setCreDate(date);
                        shukkaH.setUpdDate(date);
                        shukkaH.setCreUserId(user.id);
                        shukkaH.setUpdUserId(user.id);
                        //form 表单填写的数据
                        shukkaH.setContenerId(shukkaHForm.getContenerId());
                        shukkaH.setCustomerId(shukkaHForm.getCustomerId());
                        shukkaH.setSaleCd(shukkaHForm.getSaleCd());
                    } else if(null != split && split.length == 13) {
                        ShukkaD shukkaD = new ShukkaD();
                        String code = split[3];
                        Product1 p1 = productService.findProduct1ByCode(code);
                        Product2 p2 = productService.findProduct2ByCode(code);
                        Integer qty = Integer.parseInt(split[7]);//数量
                        Integer caseQty = Integer.parseInt(split[6]); //箱数
                        shukkaD.setJanCd(code);
                        shukkaD.setCaseQty(caseQty);
                        shukkaD.setQty(qty);
                        shukkaD.setCustomId("");
                        shukkaD.setJanNameJp(null == p1 ? "" : p1.getName());
                        shukkaD.setJanNameCn(null == p2 ? "" : p2.getName());
                        shukkaD.setUnitQty((caseQty == 0 ? 0d : qty / caseQty));
                        //shukkaD.setUnitPrice(0d);// ???? 从哪里取 ?
                        shukkaD.setGrossWeight(null == p2 ? 0d : p2.getGrossWeight());
                        shukkaD.setNetWeight(null == p2 ? 0d : p2.getNetWeight());
                        shukkaD.setMadeinCountry("日本");

                        Date date = new Date();
                        shukkaD.setCreDate(date);
                        shukkaD.setUpdDate(date);
                        shukkaD.setCreUserId(user.id);
                        shukkaD.setUpdUserId(user.id);
                        //form表单中填写的数据
                        shukkaD.setContenerId(shukkaHForm.getContenerId());
                        shukkaDList.add(shukkaD);
                    }
                }
            }
            importData.put(shukkaH, shukkaDList);
            shukkaService.save(importData);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "保存数据失败");
            e.printStackTrace();
        } finally {
            try {
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        redirectAttributes.addFlashAttribute("message", "导入数据成功!");

        return ImmutableMap.of("success", true);
    }

}
