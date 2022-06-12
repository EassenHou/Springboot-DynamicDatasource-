package cn.eassen.mydynamicdatasource.service;

import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (AaaConfig)表服务接口
 *
 * @author Auto
 * @since 2022-05-30 16:36:35
 */

@Mapper
public interface AaaConfigService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<AaaConfig> queryByCenterId(String id);

    /**
     * 新增数据
     *
     * @param aaaConfig 实例对象
     * @return 实例对象
     */
    AaaConfig insert(AaaConfig aaaConfig);

    /**
     * 修改数据
     *
     * @param aaaConfig 实例对象
     * @return 实例对象
     */
    AaaConfig update(AaaConfig aaaConfig);

    /**
     * 通过主键删除数据
     *
     * @param centerId 主键
     * @return 是否成功
     */
    boolean deleteByCenterId(String centerId);

}
