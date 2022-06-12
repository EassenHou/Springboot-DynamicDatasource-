package cn.eassen.mydynamicdatasource.shardingsphere;

import cn.eassen.mydynamicdatasource.dao.AShardingRouteDao;
import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import cn.eassen.mydynamicdatasource.service.AShardingRouteServiceImpl;
import cn.eassen.mydynamicdatasource.service.impl.AShardingRouteService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 数据库分片策略
 * @author eassen
 */
@Slf4j
public class MyComplexDatasourceRoutingAlgorithm implements ComplexKeysShardingAlgorithm<String> {
    

    final AShardingRouteService routeService = new AShardingRouteServiceImpl();
    /**
     * 用户数据源
     */
    private static final String DS_0 = "ds0";
    
    /**
     * 订单数据源
     */
    private static final String DS_1 = "ds1";

    private static final String DEFAULT_DS = DS_0;




    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        if(shardingValue.getColumnNameAndShardingValuesMap().size() <= 0){
            return Lists.newArrayList(DEFAULT_DS);
        }
        Optional<String> first = shardingValue.getColumnNameAndShardingValuesMap().entrySet().stream().findFirst().get().getValue().stream().findFirst();
        if(!first.isPresent() || "".equals(first.get())){
            return Lists.newArrayList(DEFAULT_DS);
        }
        List<String> dataBases = routeService.getDataBase(first.get());
        if(CollectionUtils.isEmpty(dataBases)){
            return Lists.newArrayList(DEFAULT_DS);
        }
        return dataBases;
    }
}
