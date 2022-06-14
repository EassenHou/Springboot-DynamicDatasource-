package cn.eassen.mydynamicdatasource.service;

import cn.eassen.mydynamicdatasource.config.ShardingConfig;
import cn.eassen.mydynamicdatasource.dao.AShardingRouteDao;
import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author eassen
 * @Create 2022/6/9 20:52
 */
@Configuration
@Slf4j
public class InitService {

    @Autowired
    AShardingRouteDao aShardingRouteDao;

    @Autowired
    ShardingConfig shardingConfig;

    @PostConstruct
    public void initShardingRoutes() {
        doInitShardingRoutes();
    }

    public void doInitShardingRoutes() {
        ConcurrentHashMap<String, List<String>> routeMap = shardingConfig.shardingRouteMap();
        List<ShardingRoute> shardingRoutes = aShardingRouteDao.queryAll();
        if (checkAndLoad(routeMap, shardingRoutes)) {
            return;
        }
        shardingConfig.setMap(routeMap);
    }

    /**
     * 检查并装载路由规则
     * @param routeMap
     * @param shardingRoutes
     * @return
     */
    private boolean checkAndLoad(ConcurrentHashMap<String, List<String>> routeMap, List<ShardingRoute> shardingRoutes) {
        if (CollectionUtils.isEmpty(shardingRoutes)) {
            log.info("待初始化数据源路由为空");
            return true;
        }
        for (ShardingRoute route : shardingRoutes) {
            if (!route.getDbNode().startsWith("ds")) {
                log.error("数据库中centerId:{}数据源配置不合法,跳过初始化", route.getDbNode());
                continue;
            }
            routeMap.put(route.getCenterId(), Lists.newArrayList(route.getDbNode()));
        }
        return false;
    }


    public void reloadShardingRouteMap(String centerId) {
        ConcurrentHashMap<String, List<String>> routeMap = shardingConfig.shardingRouteMap();
        if (!StringUtils.isEmpty(centerId)) {
            List<ShardingRoute> shardingRoutes = aShardingRouteDao.queryByCenterId(centerId);
            if (checkAndLoad(routeMap, shardingRoutes)) {
                return;
            }
            shardingConfig.setMap(routeMap);
            log.info("更新{}分片路由缓存成功！", centerId);
            return;
        }
        doInitShardingRoutes();
        log.info("全量更新分片路由缓存成功！");
    }
}
