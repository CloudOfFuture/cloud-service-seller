package com.kunlun.api.mapper;

import com.github.pagehelper.Page;
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
    Store findById(@Param("id") Long id);

    /**
     *修改店铺状态
     *
     * @param status
     * @param id
     * @return
     */
    int updateStatus(@Param("status") String status,@Param("id") Long id);

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
    Store findByUserId(@Param("userId") Long userId);

    /**
     * 获取店铺申请列表
     *
     * @param audit
     * @param searchKey
     * @return
     */
    Page<Store> findPage(@Param("audit") String audit,
                         @Param("searchKey") String searchKey);

    /**
     * 校验是否是管理员用户
     *
     * @param userId
     * @return
     */
    int validAdmin(@Param("userId") Long userId);

    /**
     * 审核
     *
     * @param audit
     * @param reason
     * @param id
     * @return
     */
    Integer audit(@Param("audit") String audit, @Param("reason") String reason,@Param("id") Long id);
}
