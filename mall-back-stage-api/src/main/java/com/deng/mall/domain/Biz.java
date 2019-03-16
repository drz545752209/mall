package com.deng.mall.domain;

import java.io.Serializable;

/**
 * biz
 * @author 
 */
public class Biz implements Serializable {
    private Integer id;

    private String name;

    private String pwd;

    /**
     * 商户评分
     */
    private Integer bizScore;

    /**
     * 联系方式
     */
    private String contantWay;

    /**
     * 所在地
     */
    private String location;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getBizScore() {
        return bizScore;
    }

    public void setBizScore(Integer bizScore) {
        this.bizScore = bizScore;
    }

    public String getContantWay() {
        return contantWay;
    }

    public void setContantWay(String contantWay) {
        this.contantWay = contantWay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        Biz other = (Biz) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPwd() == null ? other.getPwd() == null : this.getPwd().equals(other.getPwd()))
            && (this.getBizScore() == null ? other.getBizScore() == null : this.getBizScore().equals(other.getBizScore()))
            && (this.getContantWay() == null ? other.getContantWay() == null : this.getContantWay().equals(other.getContantWay()))
            && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPwd() == null) ? 0 : getPwd().hashCode());
        result = prime * result + ((getBizScore() == null) ? 0 : getBizScore().hashCode());
        result = prime * result + ((getContantWay() == null) ? 0 : getContantWay().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", pwd=").append(pwd);
        sb.append(", bizScore=").append(bizScore);
        sb.append(", contantWay=").append(contantWay);
        sb.append(", location=").append(location);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}