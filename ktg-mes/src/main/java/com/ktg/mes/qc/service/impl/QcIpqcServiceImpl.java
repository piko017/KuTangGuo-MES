package com.ktg.mes.qc.service.impl;

import java.util.List;
import com.ktg.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktg.mes.qc.mapper.QcIpqcMapper;
import com.ktg.mes.qc.domain.QcIpqc;
import com.ktg.mes.qc.service.IQcIpqcService;

/**
 * 过程检验单Service业务层处理
 * 
 * @author yinjinlu
 * @date 2022-08-29
 */
@Service
public class QcIpqcServiceImpl implements IQcIpqcService 
{
    @Autowired
    private QcIpqcMapper qcIpqcMapper;

    /**
     * 查询过程检验单
     * 
     * @param ipqcId 过程检验单主键
     * @return 过程检验单
     */
    @Override
    public QcIpqc selectQcIpqcByIpqcId(Long ipqcId)
    {
        return qcIpqcMapper.selectQcIpqcByIpqcId(ipqcId);
    }

    /**
     * 查询过程检验单列表
     * 
     * @param qcIpqc 过程检验单
     * @return 过程检验单
     */
    @Override
    public List<QcIpqc> selectQcIpqcList(QcIpqc qcIpqc)
    {
        return qcIpqcMapper.selectQcIpqcList(qcIpqc);
    }

    /**
     * 新增过程检验单
     * 
     * @param qcIpqc 过程检验单
     * @return 结果
     */
    @Override
    public int insertQcIpqc(QcIpqc qcIpqc)
    {
        qcIpqc.setCreateTime(DateUtils.getNowDate());
        return qcIpqcMapper.insertQcIpqc(qcIpqc);
    }

    /**
     * 修改过程检验单
     * 
     * @param qcIpqc 过程检验单
     * @return 结果
     */
    @Override
    public int updateQcIpqc(QcIpqc qcIpqc)
    {
        qcIpqc.setUpdateTime(DateUtils.getNowDate());
        return qcIpqcMapper.updateQcIpqc(qcIpqc);
    }

    /**
     * 批量删除过程检验单
     * 
     * @param ipqcIds 需要删除的过程检验单主键
     * @return 结果
     */
    @Override
    public int deleteQcIpqcByIpqcIds(Long[] ipqcIds)
    {
        return qcIpqcMapper.deleteQcIpqcByIpqcIds(ipqcIds);
    }

    /**
     * 删除过程检验单信息
     * 
     * @param ipqcId 过程检验单主键
     * @return 结果
     */
    @Override
    public int deleteQcIpqcByIpqcId(Long ipqcId)
    {
        return qcIpqcMapper.deleteQcIpqcByIpqcId(ipqcId);
    }
}
