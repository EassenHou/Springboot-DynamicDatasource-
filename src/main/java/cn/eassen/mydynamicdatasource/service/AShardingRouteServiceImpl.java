package cn.eassen.mydynamicdatasource.service;

import cn.eassen.mydynamicdatasource.dao.AShardingRouteDao;
import cn.eassen.mydynamicdatasource.dao.AaatestDao;
import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import cn.eassen.mydynamicdatasource.service.impl.AShardingRouteService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author eassen
 * @Create 2022/6/9 16:41
 */
@Slf4j
@Service
public class AShardingRouteServiceImpl implements AShardingRouteService {

    @Autowired
    AShardingRouteDao routeDao;
    @Autowired
    AaatestDao aaatestDao;
    @Autowired
    private WebApplicationContext applicationContext;

    private final ConcurrentHashMap<String, List<String>> routeMap = new ConcurrentHashMap<>();

    @Override
    public List<String> getDataBase(String shardingKey) {
        List<String> cacheDataBases = routeMap.get(shardingKey);
        if (!CollectionUtils.isEmpty(cacheDataBases)) {
            return cacheDataBases;
        }
//
//        Object shardingRouteDao = applicationContext.getBean("shardingRouteDao");
//        List<ShardingRoute> shardingRoutes = routeDao.queryByCenterId(shardingKey);
//        if (CollectionUtils.isEmpty(shardingRoutes)) {
//            return new ArrayList<>();
//        }
//        List<String> dataBases = shardingRoutes.stream().map(ShardingRoute::getDbNode).collect(Collectors.toList());
//        for (String dataBase : dataBases) {
//            if (!dataBase.startsWith("ds")) {
//                log.error("数据库中数据源配置不合法");
//                dataBases.remove(dataBase);
//            }
//        }
//        if (!CollectionUtils.isEmpty(shardingRoutes)) {
//            routeMap.put(shardingKey, dataBases);
//        }
        return new ArrayList<>();
    }

    @Override
    public void removeRouteCache(String centerId) {
        if (!StringUtils.isEmpty(centerId)) {
            routeMap.remove(centerId);
            log.info("移除{}路由缓存成功！", centerId);
            return;
        }
        routeMap.clear();
        log.info("移除路由缓存成功！");
    }

}