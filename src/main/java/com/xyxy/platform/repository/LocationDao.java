package com.xyxy.platform.repository;

import com.xyxy.platform.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LocationDao extends PagingAndSortingRepository<Location, Long>, JpaSpecificationExecutor<Location> {

}
