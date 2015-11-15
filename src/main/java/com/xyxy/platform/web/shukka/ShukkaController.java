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

    @Autowired
    private ShukkaService shukkaService;

    @Autowired
    private ProductService productService;


    @RequestMapping(method = RequestMethod.GET)
    public String login(Model model) {
        List<ShukkaH> shukkaHList = shukkaService.findShukkahAll();
        model.addAttribute("shukkaHList", shukkaHList);
        return "shukka/list";
    }

    @RequestMapping(value = "import")
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
    }


    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(@RequestBody ShukkahsForm shukkahsForm) {
        shukkaService.update(shukkahsForm.getShukkaHList());
        return ImmutableMap.of("success", true);
    }

}
