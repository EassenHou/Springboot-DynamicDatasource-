package cn.eassen.mydynamicdatasource;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 这里一定要排除这里的SpringBootConfiguration，因为我们已经自定义了DataSource，所以需要排序shardingjdbc设置的DataSource
 * @author eassen
 */
//@SpringBootApplication(exclude = {SpringBootConfiguration.class})
@SpringBootApplication
@MapperScan({"cn.eassen.mydynamicdatasource.dao", "cn.eassen.mydynamicdatasource.route.dao"})
public class SpringbootDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDynamicDatasourceApplication.class, args);
    }
}
