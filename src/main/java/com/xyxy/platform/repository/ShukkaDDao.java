package com.xyxy.platform.repository;

import com.xyxy.platform.entity.Product1;
import com.xyxy.platform.entity.ShukkaD;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShukkaDDao extends PagingAndSortingRepository<ShukkaD, Long>, JpaSpecificationExecutor<ShukkaD> {


}
