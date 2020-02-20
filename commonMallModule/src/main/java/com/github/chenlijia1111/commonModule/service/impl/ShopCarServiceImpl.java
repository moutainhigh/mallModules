package com.github.chenlijia1111.commonModule.service.impl;

import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodSpecVo;
import com.github.chenlijia1111.commonModule.common.responseVo.product.GoodVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarGroupByShopVo;
import com.github.chenlijia1111.commonModule.common.responseVo.shopCar.ClientShopCarVo;
import com.github.chenlijia1111.commonModule.dao.GoodSpecMapper;
import com.github.chenlijia1111.commonModule.dao.GoodsMapper;
import com.github.chenlijia1111.commonModule.dao.ShopCarMapper;
import com.github.chenlijia1111.commonModule.entity.ShopCar;
import com.github.chenlijia1111.commonModule.service.ShopCarServiceI;
import com.github.chenlijia1111.utils.common.Result;
import com.github.chenlijia1111.utils.core.PropertyCheckUtil;
import com.github.chenlijia1111.utils.list.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 购物车
 *
 * @author chenLiJia
 * @since 2020-02-19 17:37:09
 **/
@Service
public class ShopCarServiceImpl implements ShopCarServiceI {


    @Resource
    private ShopCarMapper shopCarMapper;//购物车
    @Resource
    private GoodsMapper goodsMapper;//商品
    @Resource
    private GoodSpecMapper goodSpecMapper;//商品规格


    /**
     * 添加
     *
     * @param params 添加参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-19 17:37:09
     **/
    @Override
    public Result add(ShopCar params) {

        int i = shopCarMapper.insertSelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 编辑
     *
     * @param params 编辑参数
     * @return com.github.chenlijia1111.utils.common.Result
     * @author chenLiJia
     * @since 2020-02-19 17:37:09
     **/
    @Override
    public Result update(ShopCar params) {

        int i = shopCarMapper.updateByPrimaryKeySelective(params);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 批量删除购物车
     *
     * @param shopCarIdList 1
     * @return com.logicalthinking.jiuyou.common.pojo.Result
     * @author chenlijia
     * @since 上午 11:26 2019/8/17 0017
     **/
    @Override
    public Result batchDelete(List<Integer> shopCarIdList) {
        //校验参数
        if (Lists.isEmpty(shopCarIdList)) {
            return Result.failure("请选择要删除的商品");
        }

        Integer i = shopCarMapper.batchDelete(shopCarIdList);
        return i > 0 ? Result.success("操作成功") : Result.failure("操作失败");
    }

    /**
     * 查找用户购物车中商品种类总数量
     *
     * @param clientId 1
     * @return java.lang.Integer
     * @author chenlijia
     * @since 下午 12:41 2019/8/17 0017
     **/
    @Override
    public Integer findShopCarAllGoodsKindCount(String clientId) {
        return shopCarMapper.findShopCarAllGoodsKindCount(clientId);
    }

    /**
     * 查询客户端购物车列表
     * 已商家进行分组
     * 排序按对应的最近的交易时间进行排序
     * 先查询出商家，在查询商家对应的购物车列表
     *
     * @param clientId 2
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarGroupByShopVo>
     * @author chenlijia
     * @since 下午 12:49 2019/8/17 0017
     **/
    @Override
    public List<ClientShopCarGroupByShopVo> listPageWithClient(String clientId) {
        //先根据商家进行分组查询
        List<ClientShopCarGroupByShopVo> clientShopCarGroupByShopVoList = shopCarMapper.listPageWithClientShopCarGroupByShopVo(clientId);
        if (Lists.isNotEmpty(clientShopCarGroupByShopVoList)) {
            //商家id集合
            Set<Integer> shopIdSet = clientShopCarGroupByShopVoList.stream().map(e -> e.getShopId()).collect(Collectors.toSet());
            //根据分好组的进行查询相关的购物车数据
            List<ClientShopCarVo> clientShopCarVos = shopCarMapper.listPageByClientAndShopIdSet(shopIdSet, clientId);
            //关联出商品信息-查询商品价格
            Set<String> goodsIdSet = clientShopCarVos.stream().map(e -> e.getGoodId()).collect(Collectors.toSet());
            List<GoodVo> goodVos = goodsMapper.listByGoodIdSet(goodsIdSet);
            //关联查询商品属性-查询商品 sku名称
            List<GoodSpecVo> propertyGoodsList = goodSpecMapper.listGoodSpecVoByGoodIdSet(goodsIdSet);

            //关联这些
            for (ClientShopCarVo shopCarVo : clientShopCarVos) {
                shopCarVo.findGoodsInfo(goodVos) //查询商品价格信息
                        .findGoodsSkuName(propertyGoodsList); //查询商品sku名称
            }

            //查找商家分组下的购物车列表
            for (ClientShopCarGroupByShopVo vo : clientShopCarGroupByShopVoList) {
                vo.findShopCarList(clientShopCarVos);
            }
        }
        return clientShopCarGroupByShopVoList;
    }

    /**
     * 通过购物车 id 集合查询购物车信息
     *
     * @param shopCarIdList 1
     * @return java.util.List<com.logicalthinking.jiuyou.common.reponseVo.shopCar.ClientShopCarVo>
     * @author chenlijia
     * @since 14:07 2019/8/23
     **/
    @Override
    public List<ClientShopCarVo> listByShopCarIdList(List<Integer> shopCarIdList, String currentUserId) {
        if (Lists.isEmpty(shopCarIdList) || Objects.isNull(currentUserId)) {
            return new ArrayList<>();
        }

        Set<Integer> collect = shopCarIdList.stream().collect(Collectors.toSet());
        List<ClientShopCarVo> clientShopCarVos = shopCarMapper.listPageByShopCarIdSetAndClientId(collect, currentUserId);
        //关联出商品信息-查询商品价格
        Set<String> goodsIdSet = clientShopCarVos.stream().map(e -> e.getGoodId()).collect(Collectors.toSet());
        List<GoodVo> goodVos = goodsMapper.listByGoodIdSet(goodsIdSet);
        //关联查询商品属性-查询商品 sku名称
        List<GoodSpecVo> goodSpecVos = goodSpecMapper.listGoodSpecVoByGoodIdSet(goodsIdSet);
        //关联这些
        for (ClientShopCarVo shopCarVo : clientShopCarVos) {
            shopCarVo.findGoodsInfo(goodVos) //查询商品价格信息
                    .findGoodsSkuName(goodSpecVos); //查询商品sku名称
        }
        return clientShopCarVos;
    }

    /**
     * 条件查询
     *
     * @param condition
     * @return
     * @author chenLiJia
     * @since 2020-02-19 17:37:09
     **/
    @Override
    public List<ShopCar> listByCondition(ShopCar condition) {

        PropertyCheckUtil.transferObjectNotNull(condition, true);
        return shopCarMapper.select(condition);
    }


}