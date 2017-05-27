package com.quancheng.achilles.service.utils;

import java.util.Iterator;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author liujiejian
 * @version 2016年9月11日
 */
public class BatisPage<T> implements Page<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Long    total;
    private List<T> content;

    public BatisPage(Integer pageNum, Integer pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public BatisPage(){
        super();
    }

    @Override
    public int getNumber() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public int getSize() {
        return pageSize;
    }

    @Override
    public int getNumberOfElements() {
        if (content != null) {
            return content.size();
        }
        return 0;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean hasContent() {
        if (content != null) {
            return false;
        }
        return true;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        return hasNext() ? pageable.next() : null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public int getTotalPages() {
        int totalPages = (total.intValue() + pageSize - 1) / pageSize;
        return totalPages != 0 ? totalPages : 1;
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return null;
    }

}
