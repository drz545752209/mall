package com.deng.seckill.service.impl;


import com.deng.seckill.bo.GoodsBo;
import com.deng.seckill.dao.SeckillOrderDAO;
import com.deng.seckill.dao.SeckillOrderDetailDAO;
import com.deng.seckill.domain.SeckillOrder;
import com.deng.seckill.domain.SeckillOrderDetail;
import com.deng.seckill.domain.SeckillOrderDetailExample;
import com.deng.seckill.service.SeckillGoodsService;
import com.deng.seckill.service.SeckillOrderService;
import com.hlju.mall.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("seckillOrderService")
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    SeckillGoodsService seckillGoodsService;


    @Autowired
    SeckillOrderDAO seckillOrderDAO;

    @Autowired
    SeckillOrderDetailDAO seckillOrderDetailDAO;

    @Override
    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        return seckillOrderDAO.selectByUserIdAndGoodsId(userId , goodsId);
    }

    @Transactional
    @Override
    public SeckillOrderDetail insert(User user, GoodsBo goods) {
        //秒杀商品库存减一
        int success = seckillGoodsService.reduceStock(goods.getId());
        if(success == 1) {
            SeckillOrderDetail orderInfo = new SeckillOrderDetail();
            orderInfo.setCreateDate(new Date());
            orderInfo.setGoodsCount(1);
            orderInfo.setGoodsId(goods.getId().intValue());
            orderInfo.setGoodsName(goods.getGoodsName());
            orderInfo.setGoodsPrice(goods.getSeckillPrice());
            orderInfo.setStatus(0);
            orderInfo.setUserId(user.getId());
            //添加信息进订单
            seckillOrderDetailDAO.insertSelective(orderInfo);
            Integer orderDetailId=orderInfo.getId();
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setGoodsId(goods.getId().intValue());
            seckillOrder.setOrderId(orderDetailId);
            seckillOrder.setUserId(user.getId());
            //插入秒杀表
            seckillOrderDAO.insertSelective(seckillOrder);
            return orderInfo;
        }else {
            return null;
        }
    }

    @Override
    public SeckillOrderDetail getSeckillOrderDetailByUserId(Integer userId) {
        SeckillOrderDetailExample seckillOrderDetailExample=new SeckillOrderDetailExample();
        SeckillOrderDetailExample.Criteria criteria=seckillOrderDetailExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<SeckillOrderDetail> seckillOrderDetails=seckillOrderDetailDAO.selectByExample(seckillOrderDetailExample);
        return seckillOrderDetails.get(0);
    }

    public boolean changeStatus(SeckillOrderDetail seckillOrderDetail){
        int result=seckillOrderDetailDAO.updateByPrimaryKeySelective(seckillOrderDetail);
        if (result>0){
            return true;
        }else {
            return false;
        }
    }

}
