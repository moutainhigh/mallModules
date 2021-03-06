package com.github.chenlijia1111.fightGroup.service.impl;

import com.github.chenlijia1111.fightGroup.dao.FightGroupUserOrderMapper;
import com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder;
import com.github.chenlijia1111.fightGroup.service.FightGroupUserOrderServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拼团订单记录
 *
 * @author chenLiJia
 * @since 2019-11-26 12:09:02
 **/
@Service
public class FightGroupUserOrderServiceImpl implements FightGroupUserOrderServiceI {


    @Resource
    private FightGroupUserOrderMapper fightGroupUserOrderMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result add(FightGroupUserOrder params) {

        int i = fightGroupUserOrderMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-26 12:09:02
     **/
    public Result update(FightGroupUserOrder params) {

        int i = fightGroupUserOrderMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 通过订单编号查询拼团订单记录
     * 因为订单编号是唯一的,所以可以直接用订单编号进行查询
     *
     * @param orderNo 订单编号
     * @return com.github.chenlijia1111.fightGroup.entity.FightGroupUserOrder
     * @since 上午 10:01 2019/11/27 0027
     **/
    @Override
    public FightGroupUserOrder findByOrderNo(String orderNo) {
        if (StringUtils.isNotEmpty(orderNo)) {
            FightGroupUserOrder fightGroupUserOrderCondition = new FightGroupUserOrder().setOrderNo(orderNo);
            return fightGroupUserOrderMapper.selectOne(fightGroupUserOrderCondition);
        }
        return null;
    }


}
