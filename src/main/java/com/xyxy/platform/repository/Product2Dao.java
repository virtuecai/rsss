package com.xyxy.platform.repository;

import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.Product2;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface Product2Dao extends PagingAndSortingRepository<Product2, Long>, JpaSpecificationExecutor<Product2> {

}
