package com.quancheng.achilles.service.model;

import java.io.Serializable;

public class PageInfo implements Serializable {

    private static final long serialVersionUID = 2757419959729639182L;

    private Long           number=0L;                                // 页码
    private Long           pageSize=0L;                               // 每页大小
    private Long              totalElements=0L;                              // 总数量
    private Long           totalPages=0L;                                  // 总页数
    
    public Boolean hasNext(){
        return  totalPages>number+1;
    }
    public Boolean hasPrevious(){
        return number>1;
    }
    public Long getNumber() {
        return number;
    }
    public void setNumber(Long number) {
        this.number = number;
    }
    public Long getPageSize() {
        return pageSize;
    }
    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
    public Long getTotalElements() {
        return totalElements;
    }
    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
    public Long getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
    public PageInfo(Long number, Long pageSize, Long totalElements, Long totalPages) {
        super();
        this.number = number;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
    public PageInfo( ) {
        super();
    }
    public PageInfo(Long number, Long pageSize ) {
        super();
        this.number = number;
        this.pageSize = pageSize;
    }
    public PageInfo next(){
        return !hasNext() ? null: new PageInfo(number+1,pageSize,totalElements,totalPages);
    }
}
