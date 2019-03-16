package com.deng.mall.domain;

import java.io.Serializable;

/**
 * order_detail
 * @author 
 */
public class OrderDetail implements Serializable {
    private Integer id;

    private Integer productId;

    /**
     * 消费金额
     */
    private Long sumConsume;

    private String status;

    /**
     * 购买数量
     */
    private Long countConsume;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getSumConsume() {
        return sumConsume;
    }

    public void setSumConsume(Long sumConsume) {
        this.sumConsume = sumConsume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCountConsume() {
        return countConsume;
    }

    public void setCountConsume(Long countConsume) {
        this.countConsume = countConsume;
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
        OrderDetail other = (OrderDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getSumConsume() == null ? other.getSumConsume() == null : this.getSumConsume().equals(other.getSumConsume()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCountConsume() == null ? other.getCountConsume() == null : this.getCountConsume().equals(other.getCountConsume()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getSumConsume() == null) ? 0 : getSumConsume().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCountConsume() == null) ? 0 : getCountConsume().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", sumConsume=").append(sumConsume);
        sb.append(", status=").append(status);
        sb.append(", countConsume=").append(countConsume);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}