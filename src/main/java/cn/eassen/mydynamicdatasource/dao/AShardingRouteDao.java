package cn.eassen.mydynamicdatasource.dao;


import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分片路由表(AShardingRoute)表数据库访问层
 *
 * @author Auto
 * @since 2022-06-09 16:03:26
 */
@Mapper
public interface AShardingRouteDao {

    /**
     * 通过ID查询单条数据
     *
     * @param centerId 分片键
     * @return 实例对象
     */
    List<ShardingRoute> queryByCenterId(String centerId);

    /**
     * 查询全部
     *
     * @return 实例对象
     */
    List<ShardingRoute> queryAll();
}

