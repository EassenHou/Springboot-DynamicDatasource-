package cn.eassen.mydynamicdatasource.service;

import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import cn.eassen.mydynamicdatasource.entity.AaaSubSubTest;
import cn.eassen.mydynamicdatasource.entity.AaaSubTest;
import cn.eassen.mydynamicdatasource.entity.Aaatest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Aaatest)表服务接口
 *
 * @author Auto
 * @since 2022-05-30 16:36:35
 */
public interface AaatestService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<Aaatest> queryByCenterId(String id);

    /**
     * 新增数据
     *
     * @param aaatest 实例对象
     * @return 实例对象
     */
    Aaatest insert(Aaatest aaatest);

    /**
     * 修改数据
     *
     * @param aaatest 实例对象
     * @return 实例对象
     */
    Aaatest update(Aaatest aaatest);

    /**
     * 通过主键删除数据
     *
     * @param queryInfo 主键
     * @return 是否成功
     */
    boolean delete(Aaatest queryInfo);

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
