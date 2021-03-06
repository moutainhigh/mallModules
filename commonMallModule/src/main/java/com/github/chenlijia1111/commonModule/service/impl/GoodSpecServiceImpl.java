package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.pojo.CommonMallConstants;
import com.github.chenlijia1111.commonModule.service.GoodSpecServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.StringUtils;
import com.github.chenlijia1111.commonModule.dao.GoodSpecMapper;
import com.github.chenlijia1111.commonModule.entity.GoodSpec;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品规格
 *
 * @author chenLiJia
 * @since 2019-11-01 14:34:10
 **/
@Service(CommonMallConstants.BEAN_SUFFIX + "GoodSpecServiceI")
public class GoodSpecServiceImpl implements GoodSpecServiceI {


    @Resource
    private GoodSpecMapper goodSpecMapper;


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    public Result add(GoodSpec params) {

        int i = goodSpecMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量添加
     * @param goodSpecList
     * @return
     */
    @Override
    public Result batchAdd(List<GoodSpec> goodSpecList) {
        if(Lists.isEmpty(goodSpecList)){
            return Result.failure("商品规格为空");
        }
        int i = goodSpecMapper.batchAdd(goodSpecList);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2019-11-01 14:34:10
     **/
    public Result update(GoodSpec params) {

        int i = goodSpecMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 删除该产品的所有商品的规格值
     *
     * @param productId 1
     * @return com.github.chenlijia1111.utils.common.Result
     * @since 上午 9:38 2019/11/5 0005
     **/
    @Override
    public Result deleteByProductId(String productId) {

        if (StringUtils.isNotEmpty(productId)) {
            goodSpecMapper.deleteByProductId(productId);
        }
        return Result.success("操作成功");
    }


}
