package com.xyxy.platform.service;

import com.google.common.collect.Lists;
import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.Product2;
import com.xyxy.platform.modules.core.persistence.DynamicSpecifications;
import com.xyxy.platform.modules.core.persistence.SearchFilter;
import com.xyxy.platform.modules.core.utils.Clock;
import com.xyxy.platform.repository.Product1Dao;
import com.xyxy.platform.repository.Product2Dao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
@Component
@Transactional
public class ProductService {

    private Clock clock = Clock.DEFAULT;

    private Product1Dao product1Dao;
    private Product2Dao product2Dao;


    @Autowired
    public void setProduct1Dao(Product1Dao product1Dao) {
        this.product1Dao = product1Dao;
    }

    @Autowired
    public void setProduct2Dao(Product2Dao product2Dao) {
        this.product2Dao = product2Dao;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Product1> findAll() {
        return product1Dao.findAllByNoParams();
    }

    /**
     * 根据条件查询
     *
     * @param code   商品代码
     * @param nameJa 商品日文名
     * @param nameZh 商品中文名
     * @return
     */
    public List<Product1> findByConditions(String code, String nameJa, String nameZh) {
        if (StringUtils.isBlank(code) && StringUtils.isBlank(nameJa) && StringUtils.isBlank(nameZh)) {
            return findAll();
        }
        List<SearchFilter> searchFilters = new ArrayList<>();
        if (StringUtils.isNotBlank(code)) {
            SearchFilter filter = new SearchFilter("product2.code", SearchFilter.Operator.LIKE, code);
            searchFilters.add(filter);
        }
        if (StringUtils.isNotBlank(nameJa)) {
            SearchFilter filter = new SearchFilter("name", SearchFilter.Operator.LIKE, nameJa);
            searchFilters.add(filter);
        }
        if (StringUtils.isNotBlank(nameZh)) {
            SearchFilter filter = new SearchFilter("product2.name", SearchFilter.Operator.LIKE, nameZh);
            searchFilters.add(filter);
        }
        List<Product1> productList = product1Dao.findAll(DynamicSpecifications.bySearchFilter(searchFilters, Product1.class));
        return productList;
    }

    /**
     * 根据商品代码查找商品2数据, code 设有唯一索引
     * @param code
     * @return
     */
    public Product2 findProduct2ByCode(String code) {
        return product2Dao.findByCode(code);
    }
    public Product1 findProduct1ByCode(String code) {
        return product1Dao.findByProduct2Code(code);
    }

    public void save(List<Product1> product1List, Long userId) {
        for(Product1 p1: product1List) {
            Product1 product1 = product1Dao.findOne(p1.getId());
            product1.setUpdateUserId(userId);
            product1.setUpdateDate(clock.getCurrentDate());
            product1.setName(p1.getName());
            product1.getProduct2().setName(p1.getProduct2().getName());
            product1.getProduct2().setUpdateDate(clock.getCurrentDate());
        }
        product1Dao.save(product1List);
    }
}
