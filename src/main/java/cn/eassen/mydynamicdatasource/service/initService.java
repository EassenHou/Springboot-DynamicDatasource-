package cn.eassen.mydynamicdatasource.service;

import cn.eassen.mydynamicdatasource.dao.AShardingRouteDao;
import cn.eassen.mydynamicdatasource.dao.AaatestDao;
import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author eassen
 * @Create 2022/6/9 20:52
 */
@Component
@Slf4j
public class initService {

    @Autowired
    AShardingRouteServiceImpl shardingRouteService;
    @Autowired
    AShardingRouteDao aShardingRouteDao;

    private final ConcurrentHashMap<String, List<String>> routeMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void initShardingRoutes() {
        List<ShardingRoute> shardingRoutes = aShardingRouteDao.queryAll();
        if (CollectionUtils.isEmpty(shardingRoutes)) {
            log.info("待初始化数据源路由为空");
            return;
        }
        for (ShardingRoute route : shardingRoutes) {
            if (!route.getDbNode().startsWith("ds")) {
                log.error("数据库中centerId:{}数据源配置不合法,跳过初始化", route.getCenterId());
                continue;
            }
            routeMap.put(route.getCenterId(), Lists.newArrayList(route.getCenterId()));
        }
    }
}
