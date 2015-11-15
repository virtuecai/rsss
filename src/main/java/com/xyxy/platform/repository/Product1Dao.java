package com.xyxy.platform.repository;

import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.Product2;
import com.xyxy.platform.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface Product1Dao extends PagingAndSortingRepository<Product1, Long>, JpaSpecificationExecutor<Product1> {

	@Query("select p from Product1 p where p.product2.updateDate < p.updateDate")
	List<Product1> findAllByNoParams();


	Product1 findByProduct2Code(String code);
}
