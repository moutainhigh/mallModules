package com.github.chenlijia1111.spike.common.requestVo.spikeOrder;

import com.github.chenlijia1111.commonModule.common.checkFunction.PositiveNumberCheck;
import com.github.chenlijia1111.commonModule.common.requestVo.order.CouponWithGoodIds;
import com.github.chenlijia1111.utils.core.annos.PropertyCheck;
import com.github.chenlijia1111.utils.core.enums.PropertyCheckType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 添加秒杀订单参数
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/25 0025 下午 3:12
 **/
@Setter
@Getter
@ApiModel
public class SpikeOrderAddParams {

    /**
     * 商品参与秒杀记录id
     *
     * @since 下午 3:13 2019/11/25 0025
     **/
    @ApiModelProperty(value = "商品参与秒杀记录id")
    @PropertyCheck(name = "商品参与秒杀记录id")
    private String spikeProductId;

    /**
     * 商品id
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品id")
    @PropertyCheck(name = "商品id")
    private String goodId;

    /**
     * 商品数量
     *
     * @since 下午 4:48 2019/11/5 0005
     **/
    @ApiModelProperty(value = "商品数量")
    @PropertyCheck(name = "商品数量", checkFunction = PositiveNumberCheck.class)
    private Integer count;

    /**
     * 收货人姓名
     *
     * @since 下午 4:51 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人姓名")
    @PropertyCheck(name = "收货人姓名")
    private String receiverName;

    /**
     * 收货人电话
     *
     * @since 下午 4:51 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人电话")
    @PropertyCheck(name = "收货人电话", checkType = PropertyCheckType.MOBILE_PHONE)
    private String receiverTelephone;

    /**
     * 收货人地址
     *
     * @since 下午 4:52 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人地址")
    @PropertyCheck(name = "收货人地址")
    private String receiverAddress;

    /**
     * 收货人地址-省份
     * 用于统计
     *
     * @since 下午 4:52 2019/11/5 0005
     **/
    @ApiModelProperty(value = "收货人地址-省份")
    @PropertyCheck(name = "收货人地址-省份")
    private String receiveProvince;

    /**
     * 备注
     *
     * @since 下午 4:52 2019/11/5 0005
     **/
    @ApiModelProperty(value = "备注")
    private String remarks;

    /**
     * 优惠券id集合以及优惠券在当前订单中对应的作用的商品id
     *
     * @since 上午 11:22 2019/11/22 0022
     **/
    @ApiModelProperty(value = "优惠券id集合以及优惠券在当前订单中对应的作用的商品id")
    private List<CouponWithGoodIds> couponWithGoodIdsList;


}