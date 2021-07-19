package com.dwc.crowdfunding.util;

import javax.jnlp.IntegrationService;
import java.util.List;

public class Page<T> {
    //当前页码
    private Integer pageno;
    //当前记录数
    private Integer pagesize;
    //总页码
    private Integer totalno;
    //总记录数
    private Integer totalsize;
    //对象列表
    private List<T> data;

    public Page(Integer pageno, Integer pagesize){
            if(pageno <= 0){
                this.pageno = 1;
            }else{
                this.pageno = pageno;
            }

        if(pagesize <= 0){
            this.pagesize = 10;
        }else{
            this.pagesize = pagesize;
        }
    }

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getTotalno() {
        return totalno;
    }

    private void setTotalno(Integer totalno) {
        this.totalno = totalno;
    }

    public Integer getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(Integer totalsize) {
        this.totalsize = totalsize;
        this.totalno = totalsize % pagesize ==0 ? (totalsize/pagesize) : (totalsize/pagesize + 1);
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public Integer getStartIndex(){
        return (pageno-1) * pagesize;
    }
}
