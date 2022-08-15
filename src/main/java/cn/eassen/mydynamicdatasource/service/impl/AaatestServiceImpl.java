package cn.eassen.mydynamicdatasource.service.impl;

import cn.eassen.mydynamicdatasource.dao.AaatestDao;
import cn.eassen.mydynamicdatasource.entity.AaaConfig;
import cn.eassen.mydynamicdatasource.entity.AaaSubSubTest;
import cn.eassen.mydynamicdatasource.entity.AaaSubTest;
import cn.eassen.mydynamicdatasource.entity.Aaatest;
import cn.eassen.mydynamicdatasource.service.AaatestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Aaatest)表服务实现类
 *
 * @author Auto
 * @since 2022-05-30 16:36:37
 */
@Service
public class AaatestServiceImpl implements AaatestService {

    @Resource
    private AaatestDao aaatestDao;

    /**
     * 通过ID查询单条数据
     *
     * @param centerid 主键
     * @return 实例对象
     */
    @Override
    public List<Aaatest> queryByCenterId(String centerid) {
        List<Aaatest> attests = this.aaatestDao.queryByCenterId(centerid);
        if(CollectionUtils.isEmpty(attests)){
            return new ArrayList<>();
        }
        return attests;
    }

    /**
     * 新增数据
     *
     * @param aaatest 实例对象
     * @return 实例对象
     */
    @Override
    public Aaatest insert(Aaatest aaatest) {
        this.aaatestDao.insert(aaatest);
        return aaatest;
    }

    /**
     * 修改数据
     *
     * @param aaatest 实例对象
     * @return 实例对象
     */
    @Override
    public Aaatest update(Aaatest aaatest) {
        this.aaatestDao.update(aaatest);
        return aaatest;
    }

    /**
     * 通过主键删除数据
     *
     * @param queryInfo 主键
     * @return 是否成功
     */
    @Override
    public boolean delete(Aaatest queryInfo) {
        return this.aaatestDao.delete(queryInfo) > 0;
    }

    @Override
    public List<AaaSubSubTest> queryList(Aaatest queryInfo) {
        return aaatestDao.queryList(queryInfo);
    }

    @Override
    public List<Aaatest> query(Aaatest queryInfo) {
        return aaatestDao.query(queryInfo);
    }
}
