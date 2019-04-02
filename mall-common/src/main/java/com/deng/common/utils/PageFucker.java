package com.deng.common.utils;

import com.deng.common.exception.ErrorCodeEnum;

import java.util.ArrayList;
import java.util.List;

public class PageFucker {

    /**
     * 默认导航页大小为10页
     */
    final static Integer navigatePageSize=10;

    /**
     * 页大小
     */
     private  int pageSize;
    /**
     * 页号
     */
     private  long pageNum;
    /**
     * 下一页号
     */
     private  long nextNum;
    /**
     * 上一页号
     */
     private  long previousNum;

    /**
     * 数据总数
     */
     private  long total;
    /**
     * 总页数
     */
     private  long pages;
    /**
     * 是否是首页
     */
     private boolean isFirst;
    /**
     * 是否是末页
     */
     private  boolean isLast;

    /**
     * 导航页号
     */
     private List<Integer> navigatepageNums;

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getNextNum() {
        return nextNum;
    }

    public void setNextNum(long nextNum) {
        this.nextNum = nextNum;
    }

    public long getPreviousNum() {
        return previousNum;
    }

    public void setPreviousNum(long previousNum) {
        this.previousNum = previousNum;
    }

    public long getDataSize() {
        return total;
    }

    public void setDataSize(long total) {
        this.total = total;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean first) {
        isFirst = first;
    }

    public boolean getIsLast() {
        return isLast;
    }

    public void setIsLast(boolean last) {
        isLast = last;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public PageFucker(int pageSize, long pageNum, long dataSize){
          this.pageSize=pageSize;
          this.pageNum=pageNum;
          this.total=dataSize;
          this.navigatepageNums=new ArrayList<>(navigatePageSize);
     }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }


    public void computePage(){
        if (pageNum<=0){
            throw new PageFuckerException(ErrorCodeEnum.PAGENUM_ERROR,"pagenums exceed scope",false);
        }

        if (total%pageSize==0){
            pages=total/pageSize;
        }else{
            pages=total/pageSize+1;
        }

        if (pageNum==pages){
            isLast=true;
        }
        if (pageNum==1){
            isFirst=true;
        }

        if (isLast){
            previousNum=pageNum-1;
        }else if (isFirst){
            nextNum=pageNum+1;
        }else {
            previousNum=pageNum-1;
            nextNum=pageNum+1;
        }

        int start,end;
        if (isFirst){
             start=1;
             end=pages>=(int)pageNum+navigatePageSize?(int)pageNum+navigatePageSize:(int)pages;
        }else if (isLast){
             start=(int)pageNum-navigatePageSize>=1?(int)pageNum-navigatePageSize:1;
             end=(int) pages;
        }else {
             start=(int)pageNum-navigatePageSize/2>=1?(int)pageNum-navigatePageSize/2:1;
             end=(int)pageNum+navigatePageSize/2<pages?(int)pageNum+navigatePageSize/2:(int)pages;
        }

        while (start<=end){
            navigatepageNums.add(start);
            start++;
        }
    }
}
