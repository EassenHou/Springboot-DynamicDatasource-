package cn.eassen.mydynamicdatasource.shardingsphere;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库分片策略
 *
 * @author eassen
 */
@Slf4j
public class MyComplexDatasourceRoutingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private ConcurrentHashMap<String, List<String>> routeMap;
    /**
     * 用户数据源
     */
    private static final String DS_0 = "ds0";

    private static final String DEFAULT_DS = DS_0;

    public void setRouteMap(ConcurrentHashMap<String, List<String>> routeMap) {
        this.routeMap = routeMap;
    }

    public List<String> getDatabases(String centerId) {
        return routeMap.getOrDefault(centerId, Lists.newArrayList(DEFAULT_DS));
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        if (shardingValue.getColumnNameAndShardingValuesMap().size() <= 0) {
            return Lists.newArrayList(DEFAULT_DS);
        }
        Optional<String> first = shardingValue.getColumnNameAndShardingValuesMap().entrySet().stream().findFirst().get().getValue().stream().findFirst();
        if (!first.isPresent() || "".equals(first.get())) {
            return Lists.newArrayList(DEFAULT_DS);
        }
        List<String> databases = getDatabases(first.get());
        log.info("分片策略选择Complex， 数据源选择======> {}", databases);
        return databases;
    }
}
