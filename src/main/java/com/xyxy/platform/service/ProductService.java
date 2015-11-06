package com.xyxy.platform.service;

import com.xyxy.platform.repository.ProductDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
@Component
@Transactional
public class ProductService {

    private ProductDao productDao;

}
