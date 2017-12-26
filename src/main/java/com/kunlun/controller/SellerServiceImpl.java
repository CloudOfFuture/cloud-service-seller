package com.kunlun.controller;

import com.kunlun.entity.Store;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@Service
public class SellerServiceImpl implements SellerService {


    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 商户店铺建立
     *
     * @param store
     * @return
     */
    @Override
    public DataRet<String> add(Store store) {
        if (store.getUserId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Integer validResult = sellerMapper.validCertification(store.getUserId());
        if (validResult == 0) {
            return new DataRet<>("ERROR", "未实名认证，不能创建店铺");
        }
        Integer validByUserResult = sellerMapper.validByUserId(store.getUserId());
        if (validByUserResult > 0) {
            return new DataRet<>("ERROR", "不可重复创建店铺");
        }
        Integer validNameResult = sellerMapper.validByName(store.getStoreName());
        if (validNameResult > 0) {
            return new DataRet<>("ERROR", "店铺名称已存在");
        }
        Integer result = sellerMapper.add(store);
        if (result <= 0) {
            return new DataRet<>("ERROR", "新增店铺失败");
        }
        return new DataRet<>("新增店铺成功");
    }


    /**
     * 修改店铺状态
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    @Override
    public DataRet<String> updateStatus(Long id, String status, String operator) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Store store = sellerMapper.findById(id);
        if (CommonEnum.DELETE.getCode().equals(store.getStatus())) {
            //已经删除的，不能修改
            return new DataRet<>("ERROR", "已经删除的，不能修改");
        }
        if (CommonEnum.CLOSE_LEADER.getCode().equals(store.getStatus())) {
            //被管理员关闭，不能修改
            return new DataRet<>("ERROR", "被管理员关闭，不能修改");
        }
        int result = sellerMapper.updateStatus(status, id);
        if (result <= 0) {
            return new DataRet<>("ERROR", "修改失败");
        }
        return new DataRet("修改成功");
    }


    /**
     * 修改店铺信息
     *
     * @param store
     * @return
     */
    @Override
    public DataRet<String> update(Store store) {
        if (store.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        store.setAudit(CommonEnum.AUDITING.getCode());
        Integer validName = sellerMapper.validByNameAndId(store.getStoreName(), store.getId());
        if (validName > 0) {
            return new DataRet<>("ERROR", "店铺名称已存在");
        }
        int result = sellerMapper.update(store);
        if (result <= 0) {
            return new DataRet<>("ERROR", "修改失败");
        }
        return new DataRet<>("修改成功");
    }

    /**
     * 查询店铺详情
     *
     * @param userId
     * @return
     */
    @Override
    public DataRet<Store> findByUserId(Long userId) {
        if (userId == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        Store store = sellerMapper.findByUserId(userId);
        if (store == null) {
            return new DataRet<>("ERROR", "查无结果");
        }
        return new DataRet<>(store);
    }
}