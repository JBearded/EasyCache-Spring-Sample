package com.ecache.test.service;

import com.ecache.annotation.Cache;
import com.ecache.annotation.LocalCache;
import com.ecache.test.RedisCache;
import com.ecache.test.dao.PageDao;
import com.ecache.test.model.BizModule;
import com.ecache.test.model.PageData;
import com.ecache.test.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 谢俊权
 * @create 2016/8/3 15:18
 */
@Service
public class PageService {

    @Autowired
    private PageDao pageDao;

    public void setPageDao(PageDao pageDao) {
        this.pageDao = pageDao;
    }

    @LocalCache(key= "biz_{$1.biz}_moduleId_{$1.moduleId}", expired= 60)
    public PageData<UserInfo> page(BizModule bm){
        long seconds = System.currentTimeMillis()/1000;
        System.out.println(seconds + " : " + "page from dao");
        return pageDao.page(bm.getBiz(), bm.getModuleId());
    }

    @Cache(key = "biz_$1_moduleId_$2", expired = 60)
    public Map<String , PageData<UserInfo>> pageMap(String biz, int moduleId){
        long seconds = System.currentTimeMillis()/1000;
        System.out.println(seconds + " : " + "pageMap from dao");
        return pageDao.pageMap(biz, moduleId);
    }

    @Cache(instance = RedisCache.class, key = "$3", expired = 60)
    public List<UserInfo> list(String biz, int moduleId, String key){
        long seconds = System.currentTimeMillis()/1000;
        System.out.println(seconds + " : " + "list from dao");
        return pageDao.page(biz, moduleId).getData();
    }

}
