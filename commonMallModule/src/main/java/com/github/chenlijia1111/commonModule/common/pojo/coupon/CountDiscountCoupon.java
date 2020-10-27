package com.github.chenlijia1111.commonModule.common.pojo.coupon;

import com.github.chenlijia1111.commonModule.entity.ShoppingOrder;
import com.github.chenlijia1111.commonModule.utils.BigDecimalUtil;
import com.github.chenlijia1111.utils.core.NumberUtil;
import com.github.chenlijia1111.utils.list.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 满数量折扣券
 *
 * @author chenlijia
 * @version 1.0
 * @since 2019/11/5 0005 下午 6:06
 **/
@Setter
@Getter
@Accessors(chain = true)
public class CountDiscountCoupon extends AbstractCoupon {

    /**
     * 优惠券代号
     *
     * @since 下午 6:08 2019/11/5 0005
     **/
    private String voucherKey = CountDiscountCoupon.class.getSimpleName();

    /**
     * 优惠券id
     *
     * @since 上午 10:13 2019/11/22 0022
     **/
    private String id;

    /**
     * 条件数量
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private Integer conditionCount;

    /**
     * 折扣率
     *
     * @since 下午 6:07 2019/11/5 0005
     **/
    private BigDecimal discount;

    /**
     * 计算当前优惠券对于目标金额可以抵扣多少金额
     *
     * @param orderList 目标订单
     * @return java.lang.Double 返回具体可以抵扣的金额
     * @since 上午 11:51 2019/11/22 0022
     **/
    @Override
    public BigDecimal calculatePayable(List<ShoppingOrder> orderList) {
        //返回总的优惠金额
        BigDecimal effectMoney = new BigDecimal("0.0");
        if (Lists.isNotEmpty(orderList)) {
            //订单商品数量
            BigDecimal goodCount = new BigDecimal("0.0");
            for (ShoppingOrder order : orderList) {
                goodCount = BigDecimalUtil.add(goodCount, order.getCount());
            }
            if (Objects.nonNull(this.getConditionCount()) && goodCount.compareTo(new BigDecimal(this.conditionCount)) >= 0) {
                //满足条件
                //享受折扣
                for (ShoppingOrder order : orderList) {
                    BigDecimal orderAmountTotal = order.getOrderAmountTotal();
                    //打完折之后的订单金额
                    BigDecimal afterDiscountOrderAmountTotal = orderAmountTotal.multiply(this.getDiscount());
                    order.setOrderAmountTotal(afterDiscountOrderAmountTotal);

                    //添加当前的优惠券进去
                    List<AbstractCoupon> couponList = order.getCouponList();
                    //当前订单优惠的金额
                    BigDecimal subMoney = orderAmountTotal.subtract(afterDiscountOrderAmountTotal);
                    //设置当前订单优惠的金额
                    this.setEffectiveMoney(subMoney);
                    couponList.add(this);
                    order.setCouponList(couponList);

                    //更新总的优惠金额
                    effectMoney = effectMoney.add(subMoney);
                }
            }
        }
        return effectMoney;
    }
}
