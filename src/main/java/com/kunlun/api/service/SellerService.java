package com.kunlun.api.service;

import com.kunlun.entity.Store;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;

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
    DataRet<String> add(Store store) throws Exception;


    /**
     * 修改店铺状态
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    DataRet<String> updateStatus(Long id, String status, Long operator);


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


    /**
     * 店铺列表
     *
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param audit
     * @param searchKey
     * @return
     */
    PageResult findPage(Long userId, Integer pageNo, Integer pageSize, String audit, String searchKey);

    /**
     * 店铺审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    DataRet<String> audit(String audit, String reason, Long id);
}
