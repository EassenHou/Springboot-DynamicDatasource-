package cn.eassen.mydynamicdatasource.config;

import cn.eassen.mydynamicdatasource.dao.AShardingRouteDao;
import cn.eassen.mydynamicdatasource.route.entity.ShardingRoute;
import cn.eassen.mydynamicdatasource.service.InitService;
import cn.eassen.mydynamicdatasource.shardingsphere.MyComplexDatasourceRoutingAlgorithm;
import cn.eassen.mydynamicdatasource.shardingsphere.MyHintDatasourceRoutingAlgorithm;
import cn.eassen.mydynamicdatasource.utils.SpringContextUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author eassen
 * @Create 2022/6/6 22:05
 */

@Configuration
@Slf4j
@AutoConfigureBefore(AShardingRouteDao.class)
public class ShardingConfig {

    @Value("${mybatis.mapper-locations}")
    private String MAPPER_PATH;

    @Resource
    private ShardingDataSource shardingDataSource;
    @Autowired
    AShardingRouteDao aShardingRouteDao;

    static MyComplexDatasourceRoutingAlgorithm myComplexDatasourceRoutingAlgorithm;

    private final static String SHARDING_COLUMNS = "centerid, center_id, centerId, ownerid";


    public ConcurrentHashMap<String, List<String>> shardingRouteMap() {
        ConcurrentHashMap<String, List<String>> routeMap = new ConcurrentHashMap<>();
        List<ShardingRoute> shardingRoutes = aShardingRouteDao.queryAll();
        for (ShardingRoute route : shardingRoutes) {
            if (!route.getDbNode().startsWith("ds")) {
                log.error("数据库中centerId:{}数据源配置不合法,跳过初始化", route.getCenterId());
                continue;
            }
            routeMap.put(route.getCenterId(), Lists.newArrayList(route.getCenterId()));
        }
        return routeMap;
    }


    @Bean("ShardingDataSource")
    public DataSource shardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = shardingDataSource.getDataSourceMap();
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
        myComplexDatasourceRoutingAlgorithm = new MyComplexDatasourceRoutingAlgorithm();
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration(SHARDING_COLUMNS, myComplexDatasourceRoutingAlgorithm));
        // 默认分表策略
        //shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("id", new MyHintDatasourceRoutingAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
    }

    public void setMap(ConcurrentHashMap<String, List<String>> routeMap){
        myComplexDatasourceRoutingAlgorithm.setRouteMap(routeMap);
    }

    /**
     * 添加表级规则配置
     *
     * @param shardingRuleConfig
     */
    private void addTableShardingStrategyConfig(ShardingRuleConfiguration shardingRuleConfig) {
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfig.getTableRuleConfigs();
        tableRuleConfigs.add(getHintTableRuleConfiguration("a_sharding_route", "ds0.a_sharding_route"));
        tableRuleConfigs.add(getTableRuleConfiguration("aaa_test", "ds${0..1}.aaa_test"));
        tableRuleConfigs.add(getTableRuleConfiguration("aaa_test_sub", "ds${0..1}.aaa_test_sub"));
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


    /**
     * 配置FactoryBean
     * @param dataSource
     * @return
     */
    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("ShardingDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = null;
        try {
            // 加载JNDI配置
            initJNDI();
            // 实例SessionFactory
            sqlSessionFactoryBean = new SqlSessionFactoryBean();
            // 配置数据源
            sqlSessionFactoryBean.setDataSource(dataSource);
            // 加载MyBatis配置文件
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
            org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(MAPPER_PATH);
            sqlSessionFactoryBean.setMapperLocations(resources);
        } catch (Exception e) {
            System.out.println("创建SqlSession连接工厂错误：{}");
        }
        return sqlSessionFactoryBean;
    }

    /**
     * 加载JNDI配置
     * @throws NamingException
     */
    private void initJNDI() throws NamingException {
        new InitialContext();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryBean") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
        return new SqlSessionTemplate(Objects.requireNonNull(sqlSessionFactoryBean.getObject()), ExecutorType.BATCH);
    }
}