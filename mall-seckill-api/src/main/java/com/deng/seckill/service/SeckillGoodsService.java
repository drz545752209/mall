package com.deng.seckill.service;


import com.deng.seckill.bo.GoodsBo;

import java.util.Date;
import java.util.List;

public interface SeckillGoodsService {

    List<GoodsBo> getSeckillGoodsList();

    GoodsBo getseckillGoodsBoByGoodsId(Long goodsId);

    int reduceStock(Long goodsId);

    boolean insertSeckillGoods(String productId, Integer price, Date startTime, Date endTime);
}
