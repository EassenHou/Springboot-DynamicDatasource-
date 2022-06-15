package cn.eassen.mydynamicdatasource.dao;

import cn.eassen.mydynamicdatasource.entity.AaaSubSubTest;
import cn.eassen.mydynamicdatasource.entity.AaaSubTest;
import cn.eassen.mydynamicdatasource.entity.Aaatest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Aaatest)表数据库访问层
 *
 * @author Auto
 * @since 2022-05-30 16:36:30
 */
public interface AaatestDao {

    /**
     * 通过ID查询单条数据
     *
     * @param centerid 主键
     * @return 实例对象
     */
    List<Aaatest> queryByCenterId(@Param("centerid") String centerid);
    /**
     * 统计总行数
     *
     * @param aaatest 查询条件
     * @return 总行数
     */
    long count(Aaatest aaatest);

    /**
     * 新增数据
     *
     * @param aaatest 实例对象
     * @return 影响行数
     */
    int insert(Aaatest aaatest);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Aaatest> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Aaatest> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Aaatest> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Aaatest> entities);

    /**
     * 修改数据
     *
     * @param aaatest 实例对象
     * @return 影响行数
     */
    int update(Aaatest aaatest);

    /**
     * 删除数据
     *
     * @param queryInfo 主键
     * @return 影响行数
     */
    int delete(Aaatest queryInfo);

    /**
     * 查询
     * @param queryInfo
     * @return
     */
    List<AaaSubSubTest> queryList(Aaatest queryInfo);

    /**
     * 查询
     * @param queryInfo
     * @return
     */
    List<Aaatest> query(Aaatest queryInfo);
}

