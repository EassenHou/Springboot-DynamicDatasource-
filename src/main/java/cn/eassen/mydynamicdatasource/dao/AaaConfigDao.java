package cn.eassen.mydynamicdatasource.dao;

import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (AaaConfig)表数据库访问层
 *
 * @author Auto
 * @since 2022-05-30 16:36:30
 */
public interface AaaConfigDao {

    /**
     * 通过ID查询单条数据
     *
     * @param centerid 主键
     * @return 实例对象
     */
    List<AaaConfig> queryByCenterId(@Param("centerid") String centerid);
    /**
     * 统计总行数
     *
     * @param aaatest 查询条件
     * @return 总行数
     */
    long count(AaaConfig aaatest);

    /**
     * 新增数据
     *
     * @param aaatest 实例对象
     * @return 影响行数
     */
    int insert(AaaConfig aaatest);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AaaConfig> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AaaConfig> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AaaConfig> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AaaConfig> entities);

    /**
     * 修改数据
     *
     * @param aaatest 实例对象
     * @return 影响行数
     */
    int update(AaaConfig aaatest);

    /**
     * 通过主键删除数据
     *
     * @param centerId 主键
     * @return 影响行数
     */
    int deleteByCenterId(String centerId);
}

