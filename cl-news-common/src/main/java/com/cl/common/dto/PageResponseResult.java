package com.cl.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PageResponseResult <T> extends ResponseResult<T> implements Serializable {
    private Long currentPage;
    private Integer size;
    private Long total;
    public PageResponseResult(Long curentPage,Integer size,Long total){
        this.currentPage=curentPage;
        this.size=size;
        this.total=total;
    }
    public PageResponseResult(Long curentPage,Integer size,T data){
        this.currentPage=curentPage;
        this.size=size;
        this.setData(data);
    }
    public PageResponseResult(Long currentPage, Integer size, Long total, T data) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
        this.setData(data);
    }
}