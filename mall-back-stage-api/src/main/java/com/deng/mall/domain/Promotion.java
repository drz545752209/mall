package com.deng.mall.domain;

import java.io.Serializable;

/**
 * promotion
 * @author  dengrz
 */
public class Promotion implements Serializable {
    private Integer id;

    /**
     * 促销信息
     */
    private String msg;

    /**
     * 折扣
     */
    private Integer dicount;

    /**
     * 商品id
     */
    private Integer productId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getDicount() {
        return dicount;
    }

    public void setDicount(Integer dicount) {
        this.dicount = dicount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Promotion other = (Promotion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getDicount() == null ? other.getDicount() == null : this.getDicount().equals(other.getDicount()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getDicount() == null) ? 0 : getDicount().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msg=").append(msg);
        sb.append(", dicount=").append(dicount);
        sb.append(", productId=").append(productId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}