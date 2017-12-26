package com.kunlun.controller;

import com.kunlun.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@Mapper
public interface SellerMapper {

    /**
     * 校验是否实名认证
     *
     * @param userId
     * @return
     */
    Integer validCertification(@Param("userId") Long userId);

    /**
     * 校验用户是否已经存在店铺
     *
     * @param userId
     * @return
     */
    Integer validByUserId(@Param("userId") Long userId);


    /**
     * 校验店铺名称是否已经存在
     *
     * @param storeName
     * @return
     */
    Integer validByName(@Param("storeName") String storeName);

    /**
     * 根据id校验店铺名称是否已经存在
     *
     * @param storeName
     * @param id
     * @return
     */
    Integer validByNameAndId(@Param("storeName") String storeName,@Param("id") Long id);

    /**
     * 店铺新增
     *
     * @param store
     * @return
     */
    Integer add(Store store);

    /**
     * 根据id获取店铺详情
     *
     * @param id
     * @return
     */
    Store findById(Long id);

    /**
     *修改店铺状态
     *
     * @param status
     * @param id
     * @return
     */
    int updateStatus(String status, Long id);

    /**
     * 修改店铺信息
     *
     * @param store
     * @return
     */
    int update(Store store);

    /**
     * 根据userId查询店铺详情
     *
     * @param userId
     * @return
     */
    Store findByUserId(Long userId);
}
