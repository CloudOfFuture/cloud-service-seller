package com.kunlun.controller;

import com.kunlun.entity.Store;
import com.kunlun.result.DataRet;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
public interface SellerService {

    /**
     * 商户店铺建立
     *
     * @param store
     * @return
     */
    DataRet<String> add(Store store);


    /**
     * 修改店铺状态
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    DataRet<String> updateStatus(Long id, String status, String operator);


    /**
     * 修改店铺信息
     *
     * @param store
     * @return
     */
    DataRet<String> update(Store store);


    /**
     * 查询店铺详情
     *
     * @param userId
     * @return
     */
    DataRet<Store> findByUserId(Long userId);
}
