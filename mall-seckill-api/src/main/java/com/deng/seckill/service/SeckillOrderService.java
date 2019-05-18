package com.deng.seckill.service;


import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.domain.SeckillOrder;
import com.deng.seckill.domain.SeckillOrderDetail;
import com.hlju.mall.domain.User;


public interface SeckillOrderService {

    SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId);

    SeckillOrderDetail insert(User user, GoodsBo goods);

    SeckillOrderDetail getSeckillOrderDetailByUserId(Integer userId);

    boolean changeStatus(SeckillOrderDetail seckillOrderDetail);

}
