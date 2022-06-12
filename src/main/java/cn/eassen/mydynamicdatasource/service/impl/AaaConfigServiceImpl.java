package cn.eassen.mydynamicdatasource.service.impl;

import cn.eassen.mydynamicdatasource.dao.AaaConfigDao;
import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import cn.eassen.mydynamicdatasource.service.AaaConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * (AaaConfig)表服务实现类
 *
 * @author Auto
 * @since 2022-05-30 16:36:37
 */
@Service
public class AaaConfigServiceImpl implements AaaConfigService {

    @Autowired
    private AaaConfigDao aaaConfigDao;

    /**
     * 通过ID查询单条数据
     *
     * @param centerid 主键
     * @return 实例对象
     */
    @Override
    public List<AaaConfig> queryByCenterId(String centerid) {
        List<AaaConfig> aaaConfigs = this.aaaConfigDao.queryByCenterId(centerid);
        if(CollectionUtils.isEmpty(aaaConfigs)){
            return new ArrayList<>();
        }
        return aaaConfigs;
    }

    /**
     * 新增数据
     *
     * @param aaaConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AaaConfig insert(AaaConfig aaaConfig) {
        this.aaaConfigDao.insert(aaaConfig);
        return aaaConfig;
    }

    /**
     * 修改数据
     *
     * @param aaaConfig 实例对象
     * @return 实例对象
     */
    @Override
    public AaaConfig update(AaaConfig aaaConfig) {
        this.aaaConfigDao.update(aaaConfig);
        return aaaConfig;
    }

    /**
     * 通过主键删除数据
     *
     * @param centerId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteByCenterId(String centerId) {
        return this.aaaConfigDao.deleteByCenterId(centerId) > 0;
    }
}
