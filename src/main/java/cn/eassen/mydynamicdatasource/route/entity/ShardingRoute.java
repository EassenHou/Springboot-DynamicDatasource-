package cn.eassen.mydynamicdatasource.route.entity;

import lombok.Data;


/**
 * 分片路由表(ShardingRoute)实体类
 *
 * @author Auto
 * @since 2022-06-09 15:59:49
 */
@Data
public class ShardingRoute {
    private String centerId;
    
    private String centerName;
    
    private String physicalDs;
    
    private String dbNode;
    
    private String tbNode;
    
    private String shard;
}

