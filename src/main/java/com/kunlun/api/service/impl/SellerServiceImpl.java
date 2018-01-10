package com.kunlun.api.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kunlun.api.client.UserClient;
import com.kunlun.api.mapper.SellerMapper;
import com.kunlun.api.service.SellerService;
import com.kunlun.entity.Store;
import com.kunlun.enums.CommonEnum;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import com.kunlun.utils.IDWorker;
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

    @Autowired
    private UserClient userClient;

    /**
     * 商户店铺建立
     *
     * @param store Store
     * @return DataRet
     */
    @Override
    public DataRet<String> add(Store store) throws Exception {
        if (store.getUserId() == null) {
            return new DataRet<>("ERROR", "参数错误");

        }
        int validResult = sellerMapper.validMobile(store.getMobile());
        if (validResult > 0) {
            return new DataRet<>("ERROR", "一个手机号只能绑定一个店铺");
        }
        validResult = sellerMapper.validCertification(store.getUserId());
        if (validResult == 0) {
            return new DataRet<>("ERROR", "未实名认证，不能创建店铺");
        }
        validResult = sellerMapper.validByUserId(store.getUserId());
        if (validResult > 0) {
            return new DataRet<>("ERROR", "不可重复创建店铺");
        }
        validResult = sellerMapper.validByName(store.getStoreName());
        if (validResult > 0) {
            return new DataRet<>("ERROR", "店铺名称已存在");
        }
        String storeNo = "HKM" + IDWorker.getFlowIdWorkerInstance().nextId() / 100000;
        store.setStoreNo(storeNo);
        int result = sellerMapper.add(store);
        if (result <= 0) {
            return new DataRet<>("ERROR", "新增店铺失败");
        }
        return new DataRet<>("新增店铺成功");
    }


    /**
     * 修改店铺状态
     *
     * @param id       店铺id
     * @param status   店铺状态 NORMAL 正常,
     *                 CLOSE_LEADER 管理员关闭,
     *                 CLOSE 关闭，
     *                 DELETE 删除状态
     * @param operator 操作人id
     * @return DataRet
     */
    @Override
    public DataRet<String> updateStatus(Long id, String status, Long operator) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        boolean isAdminUser = userClient.validAdmin(operator).isSuccess();
        if (!isAdminUser && CommonEnum.CLOSE_LEADER.getCode().equals(status)) {
            return new DataRet<>("ERROR", "只有管理员才能永久关闭");
        }
        Store store = sellerMapper.findById(id);
        if (CommonEnum.CLOSE_LEADER.getCode().equals(store.getStatus()) && !isAdminUser) {
            //被管理员关闭，不能修改
            return new DataRet<>("ERROR", "只有管理员才能修改");
        }
        if (CommonEnum.DELETE.getCode().equals(store.getStatus())) {
            //已经删除的，不能修改
            return new DataRet<>("ERROR", "已经删除的，不能修改");
        }
        int result = sellerMapper.updateStatus(status, id);
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet<>("ERROR", "修改失败");
    }


    /**
     * 修改店铺信息
     *
     * @param store Store
     * @return DataRet
     */
    @Override
    public DataRet<String> update(Store store) {
        if (store.getId() == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        store.setAudit(CommonEnum.AUDITING.getCode());
        int validResult = sellerMapper.validByNameAndId(store.getStoreName(), store.getId());
        if (validResult > 0) {
            return new DataRet<>("ERROR", "店铺名称已存在");
        }
        validResult = sellerMapper.validMobileAndId(store.getMobile(), store.getId());
        if (validResult > 0) {
            return new DataRet<>("ERROR", "该手机号基于绑定其他店铺");
        }
        int result = sellerMapper.update(store);
        if (result > 0) {
            return new DataRet<>("修改成功");
        }
        return new DataRet<>("ERROR", "修改失败");
    }

    /**
     * 查询店铺详情
     *
     * @param userId Long
     * @return Store
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


    /**
     * 店铺列表
     *
     * @param userId    Long
     * @param pageNo    Integer
     * @param pageSize  Integer
     * @param audit     审核状态 AUDITING待审核
     *                  NOT_PASS_AUDIT审核未通过
     *                  PASS_AUDIT审核通过
     * @param searchKey String
     * @return PageResult
     */
    @Override
    public PageResult findPage(Long userId, Integer pageNo, Integer pageSize, String audit, String searchKey) {
        PageHelper.startPage(pageNo, pageSize);
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
        }
        Page<Store> page = sellerMapper.findPage(audit, userId, searchKey);
        return new PageResult<>(page);
    }

    /**
     * 店铺审核
     *
     * @param audit  AUDITING 待审核
     *               NOT_PASS_AUDIT 审核未通过
     *               PASS_AUDIT 审核通过
     * @param reason 未通过原因
     * @param id     店铺id
     * @return DataRet
     */
    @Override
    public DataRet<String> audit(String audit, String reason, Long id) {
        if (id == null) {
            return new DataRet<>("ERROR", "参数错误");
        }
        if (audit.equals(CommonEnum.NOT_PASS_AUDIT.getCode()) && StringUtils.isEmpty(reason)) {
            return new DataRet<>("ERROR", "请填写未通过原因");
        }
        int result = sellerMapper.audit(audit, reason, id);
        if (result > 0) {
            return new DataRet<>("审核通过");
        }
        return new DataRet<>("ERROR", "审核失败");
    }
}
