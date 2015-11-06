package com.xyxy.platform.service;

import com.google.common.collect.Lists;
import com.xyxy.platform.entity.Location;
import com.xyxy.platform.repository.LocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
@Component
@Transactional
public class LocationService {

    private LocationDao locationDao;

    //缓存所属地区信息
    private static List<Location> locationList;
    private static Map<Long, Location> longLocationMap = new HashMap<>();

    @PostConstruct
    public void init() {
        locationList = Lists.newArrayList(locationDao.findAll());
        for(Location location: locationList) {
            longLocationMap.put(location.getId(), location);
        }
    }

    /**
     * 获得所有所属地区信息
     *
     * @return
     */
    public List<Location> findAll() {
        return locationList;
    }

    public Location getById(Long id) {
        return longLocationMap.get(id);
    }

    @Autowired
    public void setLocationDao(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

}
