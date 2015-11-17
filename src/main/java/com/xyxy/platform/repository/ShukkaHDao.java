package com.xyxy.platform.repository;

import com.xyxy.platform.entity.ShukkaH;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ShukkaHDao extends PagingAndSortingRepository<ShukkaH, Long>, JpaSpecificationExecutor<ShukkaH> {


}
