package com.deng.mall.domain;

import java.sql.Date;

public class BoOrder {
       private Integer orderId;
       private String productName;
       private Long sumConsume;
       private Long countConsume;
       private String userName;
       private String status;
       private String operator;
       private Date date;

       public Integer getOrderId() {
              return orderId;
       }

       public void setOrderId(Integer orderId) {
              this.orderId = orderId;
       }

       public String getProductName() {
              return productName;
       }

       public void setProductName(String productName) {
              this.productName = productName;
       }

       public Long getSumConsume() {
              return sumConsume;
       }

       public void setSumConsume(Long sumConsume) {
              this.sumConsume = sumConsume;
       }

       public Long getCountConsume() {
              return countConsume;
       }

       public void setCountConsume(Long countConsume) {
              this.countConsume = countConsume;
       }

       public String getUserName() {
              return userName;
       }

       public void setUserName(String userName) {
              this.userName = userName;
       }

       public String getStatus() {
              return status;
       }

       public void setStatus(String status) {
              this.status = status;
       }

       public String getOperator() {
              return operator;
       }

       public void setOperator(String operator) {
              this.operator = operator;
       }

       public Date getDate() {
              return date;
       }

       public void setDate(Date date) {
              this.date = date;
       }
}
