package cn.eassen.mydynamicdatasource.config;

import cn.eassen.mydynamicdatasource.shardingsphere.MyComplexDatasourceRoutingAlgorithm;
import cn.eassen.mydynamicdatasource.shardingsphere.MyHintDatasourceRoutingAlgorithm;
import cn.eassen.mydynamicdatasource.utils.DataSourceUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author eassen
 * @Create 2022/6/6 22:05
 */

@Configuration
public class CommonDao {

    @Value("${mybatis.mapper-locations}")
    private String MAPPER_PATH;

    private final static String SHARDING_COLUMNS = "centerid, center_id, centerId, ownerid";
    /**
     * SqlSessionFactory 实体
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setFailFast(true);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(MAPPER_PATH));
        return sessionFactory.getObject();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 未配置分片规则的表将通过默认数据源定位
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        // 分片规则列表
        addTableShardingStrategyConfig(shardingRuleConfig);
        // 绑定表规则列表（多表联合查询）
        shardingRuleConfig.getBindingTableGroups().add("aaa_test, aaa_test_sub");
        // 广播表配置 （配置表，所有分片一致）
        shardingRuleConfig.getBroadcastTables().add("aaa_config");
        // 默认分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration(SHARDING_COLUMNS, new MyComplexDatasourceRoutingAlgorithm()));
        // 默认分表策略
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyHintDatasourceRoutingAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }

    /**
     * 添加表级规则配置
     * @param shardingRuleConfig
     */
    private void addTableShardingStrategyConfig(ShardingRuleConfiguration shardingRuleConfig) {
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(getTableRuleConfiguration("aaa_test","ds${0..1}.aaa_test"));
        tableRuleConfigs.add(getTableRuleConfiguration("aaa_test_sub","ds${0..1}.aaa_test_sub"));
        tableRuleConfigs.add(getHintTableRuleConfiguration("a_sharding_route","ds0.aaa_test_sub"));

    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        return new KeyGeneratorConfiguration("SNOWFLAKE", "id");
    }

    private TableRuleConfiguration getTableRuleConfiguration(final String logicTable, final String actualDataNodes) {
        TableRuleConfiguration result = new TableRuleConfiguration(logicTable, actualDataNodes);
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    private TableRuleConfiguration getHintTableRuleConfiguration(final String logicTable, final String actualDataNodes) {
        TableRuleConfiguration result = new TableRuleConfiguration(logicTable, actualDataNodes);
        result.setDatabaseShardingStrategyConfig(new HintShardingStrategyConfiguration(new MyHintDatasourceRoutingAlgorithm()));
        return result;
    }


    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("ds0", DataSourceUtil.createDataSource(null, "localhost"));
        result.put("ds1", DataSourceUtil.createDataSource(null, "172.17.90.226"));
        return result;
    }
}