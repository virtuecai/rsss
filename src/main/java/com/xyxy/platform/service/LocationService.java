package com.xyxy.platform.service;

import com.xyxy.platform.entity.Location;
import com.xyxy.platform.repository.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
@Component
@Transactional
public class LocationService {

    private LocationDao locationDao;

    /**
     * 获得所有所属地区信息
     * @return
     */
    public Iterable<Location> findAll() {
        return locationDao.findAll();
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

}
