//package cn.eassen.mydynamicdatasource.shardingsphere;
//
//import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Collection;
//import java.util.HashSet;
//
///**
// * 数据库分片策略
// * @author eassen
// */
//public class MyPreciseDatasourceRoutingAlgorithm implements PreciseShardingAlgorithm<String> {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MyPreciseDatasourceRoutingAlgorithm.class);
//
//    /**
//     * 用户数据源
//     */
//    private static final String DS_0 = "ds0";
//
//    /**
//     * 订单数据源
//     */
//    private static final String DS_1 = "ds1";
//
//
//    /**
//     * 精准分片
//     * @param collection
//     * @param preciseShardingValue
//     * @return
//     */
//    @Override
//    public String doSharding(Collection collection, PreciseShardingValue preciseShardingValue) {
//        if("123".equals(preciseShardingValue.getValue())){
//            return DS_0;
//        }
//        return DS_1;
//    }
//}
