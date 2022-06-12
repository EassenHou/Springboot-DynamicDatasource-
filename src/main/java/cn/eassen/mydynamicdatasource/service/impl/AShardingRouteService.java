package cn.eassen.mydynamicdatasource.service.impl;

import java.util.List;

/**
 * (Aaatest)表服务接口
 *
 * @author Auto
 * @since 2022-05-30 16:36:35
 */
public interface AShardingRouteService {

    /**
     * 获取数据库
     *
     * @param shardingKey
     * @return
     */
    List<String> getDataBase(String shardingKey);

    /**
     * 移除路由缓存
     * @param centerId
     */
    void removeRouteCache(String centerId);

}

