package com.hlju.mall.service.impl;

import com.deng.common.utils.StrUntils;
import com.deng.logistics.service.LogisticsService;
import com.deng.mall.domain.Biz;
import com.deng.mall.domain.Product;
import com.deng.mall.domain.Store;
import com.deng.mall.service.BizService;
import com.deng.mall.service.OrderService;
import com.deng.mall.service.ProductService;
import com.deng.mall.service.StoreService;
import com.hlju.mall.dao.CommentDAO;
import com.hlju.mall.dao.UserDAO;
import com.hlju.mall.domain.BoComment;
import com.hlju.mall.domain.Comment;
import com.hlju.mall.domain.CommentExample;
import com.hlju.mall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    BizService bizService;
    @Autowired
    LogisticsService logisticsService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    StoreService storeService;
    @Autowired
    UserDAO userDAO;

    @Override
    @Transactional
    public boolean addComment(String commentJson) {
        Map resultMap=StrUntils.json2Map(commentJson);
        Integer orderId= Integer.valueOf(resultMap.get("orderId").toString()) ;
        Integer productId= Integer.valueOf(resultMap.get("productId").toString()) ;
        Integer logisticsScore= Integer.valueOf(resultMap.get("logisticsScore").toString());
        Integer storeScore= Integer.valueOf(resultMap.get("storeScore").toString());
        Integer storeId=orderService.getStoreIdByOrderId(orderId);
        Integer orderDetailId=orderService.getOrderDetailIdById(orderId);
        String comment= resultMap.get("commentDesc").toString();

        Comment commentObj=new Comment();
        commentObj.setComment(comment);
        commentObj.setOrderId(orderId);
        commentObj.setProductId(productId);
        commentObj.setStoreId(storeId);


        boolean var1=logisticsService.updateLogisticeScore(orderDetailId,logisticsScore);
        boolean var2=bizService.updateBizScore(storeId,storeScore);
        boolean var3=productService.updateComment(productId,comment);
        boolean var4=commentDAO.insertSelective(commentObj)==0 ? false:true;


        if (var1&&var2&&var3&var4){
            return true;
        }

        return false;
    }

    private List<BoComment> comments2CommentBo(List<Comment> comments,List<BoComment> boComments){
        for (Comment comment : comments) {
            BoComment boComment=new BoComment();
            Integer userId = orderService.getOrderById(comment.getOrderId()).getUserId();
            String userName= userDAO.selectByPrimaryKey(userId).getName();
            Product product=productService.getProductById(comment.getProductId().toString());
            String productName=product.getName();
            String commentDesc=product.getComment();
            boComment.setUserName(userName);
            boComment.setProductName(productName);
            boComment.setComment(commentDesc);

            boComments.add(boComment);
        }
        return boComments;
    }

    @Override
    public List<BoComment> getCommentBoList(String bizName) {
        Biz biz=new Biz();
        biz.setName(bizName);
        Integer bizId=bizService.selectBizNameByExamle(biz).get(0).getId();
        List<Store> stores=storeService.getStoresByBizId(bizId);
        List<Integer> storeIds=new ArrayList<>();
        for (Store store:stores){
            storeIds.add(store.getId());
        }

        CommentExample commentExample=new CommentExample();
        CommentExample.Criteria criteria=commentExample.createCriteria();
        criteria.andStoreIdIn(storeIds);

        List<Comment> comments=commentDAO.selectByExample(commentExample);
        List<BoComment> boComments=new ArrayList<>();

        boComments=comments2CommentBo(comments,boComments);

        return boComments;
    }

    @Override
    public Integer getCommentCount(Integer productId) {
        CommentExample commentExample=new CommentExample();

        CommentExample.Criteria commentExampleCriteria=commentExample.createCriteria();
        commentExampleCriteria.andProductIdEqualTo(productId);
        Integer commentCount=commentDAO.selectByExample(commentExample).size();

        return commentCount;
    }

    @Override
    public List<BoComment> getProductCommentList(Integer productId) {
        CommentExample commentExample=new CommentExample();

        CommentExample.Criteria commentExampleCriteria=commentExample.createCriteria();
        commentExampleCriteria.andProductIdEqualTo(productId);
        List<Comment> comments=commentDAO.selectByExample(commentExample);

        List<BoComment> boComments=new ArrayList<>();

        boComments=comments2CommentBo(comments,boComments);

        return boComments;
    }
}
