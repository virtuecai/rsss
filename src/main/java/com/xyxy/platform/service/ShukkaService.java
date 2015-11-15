package com.xyxy.platform.service;

import com.xyxy.platform.entity.ShukkaD;
import com.xyxy.platform.entity.ShukkaH;
import com.xyxy.platform.modules.core.utils.Clock;
import com.xyxy.platform.repository.ShukkaDDao;
import com.xyxy.platform.repository.ShukkaHDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by VirtueCai on 2015/11/6 0006.
 */
@Component
@Transactional
public class ShukkaService {

    private Clock clock = Clock.DEFAULT;

    private ShukkaHDao shukkaHDao;
    private ShukkaDDao shukkaDDao;

    @Autowired
    public void setShukkaHDao(ShukkaHDao shukkaHDao) {
        this.shukkaHDao = shukkaHDao;
    }

    @Autowired
    public void setShukkaDDao(ShukkaDDao shukkaDDao) {
        this.shukkaDDao = shukkaDDao;
    }

    /**
     * 存储一个头表信息和多个详情表信息
     * @param shukkaH 头表
     * @param shukkaDList 详情表 集合
     */
    public void save(ShukkaH shukkaH, List<ShukkaD> shukkaDList) {
        if(null == shukkaH) return;
        if(null == shukkaDList || shukkaDList.size() <= 0) return;
        ShukkaH hEntity = shukkaHDao.save(shukkaH);
        for (ShukkaD d: shukkaDList) {
            d.setShukkaHId(hEntity.getId());
        }
        //shukkaH.setShukkaDList(shukkaDList);
        //shukkaHDao.save(hEntity);
        shukkaDDao.save(shukkaDList);
    }

    /**
     * 收集 h and d 数据, 遍历Map进行存储
     * @param importData
     */
    public void save(Map<ShukkaH, List<ShukkaD>> importData) {
        for(Map.Entry<ShukkaH, List<ShukkaD>> entry : importData.entrySet()) {
            save(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 查询所有 h 信息
     * @return
     */
    public List<ShukkaH> findShukkahAll() {
        return (List<ShukkaH>)shukkaHDao.findAll();
    }

    public void update(List<ShukkaH> shukkaHList) {
        for(ShukkaH h: shukkaHList) {
            ShukkaH entity = shukkaHDao.findOne(h.getId());
            entity.setContenerId(h.getContenerId());
            entity.setCustomerId(h.getCustomerId());
            entity.setSaleCd(h.getSaleCd());
            for(ShukkaD d : entity.getShukkaDList()) {
                d.setContenerId(h.getContenerId());
            }
            shukkaHDao.save(entity);
        }
    }

}
