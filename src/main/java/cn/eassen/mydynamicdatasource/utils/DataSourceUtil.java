package cn.eassen.mydynamicdatasource.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;

/**
 * @Author eassen
 * @Create 2022/6/6 22:42
 */
public final class DataSourceUtil {

    private static final String HOST = "localhost";

    private static final int PORT = 3306;

    private static final String USER_NAME = "root";

    private static final String PASSWORD = "biosan#17";

    public static DataSource createDataSource(final String dataSourceName,
                                              final String host){
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(com.mysql.cj.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://%s:%s/acme?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8",
                host, PORT));
        result.setUsername(USER_NAME);
        result.setPassword(PASSWORD);
        return result;
    }
}
