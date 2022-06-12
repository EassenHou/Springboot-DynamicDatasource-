package cn.eassen.mydynamicdatasource.shardingsphere;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * 直接路由
 *
 * @author eassen
 */
@Slf4j
@Component
public class MyHintDatasourceRoutingAlgorithm implements HintShardingAlgorithm<String> {
    /**
     * hint分片
     *
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<String> shardingValue) {
        Collection<String> result = new HashSet<>(shardingValue.getValues());
        log.info("availableTargetNames:{},shardingValue:{},返回的数据源:{}",
                availableTargetNames, shardingValue, result);

        return Lists.newArrayList("DS0");
    }
}
