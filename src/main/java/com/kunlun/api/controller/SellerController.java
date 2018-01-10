package com.kunlun.api.controller;

import com.kunlun.api.service.SellerService;
import com.kunlun.entity.Store;
import com.kunlun.result.DataRet;
import com.kunlun.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 商户店铺建立
     *
     * @param store
     * @return
     */
    @PostMapping("/add/store")
    public DataRet<String> add(@RequestBody Store store) throws Exception {
        return sellerService.add(store);
    }

    /**
     * 修改店铺状态
     *
     * @param id
     * @param status   店铺状态 NORMAL 正常 ,
     *                 CLOSE_LEADER 管理员关闭,
     *                 CLOSE 关闭，
     *                 DELETE 删除状态
     * @param operator
     * @return
     */
    @PostMapping("/updateStatus")
    public DataRet<String> updateStatus(@RequestParam(value = "id") Long id,
                                        @RequestParam(value = "status") String status,
                                        @RequestParam(value = "operator") Long operator) {
        return sellerService.updateStatus(id, status, operator);
    }

    /**
     * 修改店铺信息
     *
     * @param store
     * @return
     */
    @PostMapping("/update")
    public DataRet<String> update(@RequestBody Store store) {
        return sellerService.update(store);
    }

    /**
     * 查询店铺详情
     *
     * @param userId Long
     * @return DataRet
     */
    @GetMapping("/findByUserId")
    public DataRet<Store> findByUserId(@RequestParam(value = "userId") Long userId) {
        return sellerService.findByUserId(userId);
    }

    /**
     * 店铺列表
     *
     * @param pageNo   Integer
     * @param pageSize Integer
     * @param audit    审核状态 AUDITING待审核
     *                 NOT_PASS_AUDIT审核未通过
     *                 PASS_AUDIT审核通过
     * @return
     */
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "userId", required = false) Long userId,
                               @RequestParam(value = "pageNo") Integer pageNo,
                               @RequestParam(value = "pageSize") Integer pageSize,
                               @RequestParam(value = "audit", required = false) String audit,
                               @RequestParam(value = "searchKey", required = false) String searchKey) {
        return sellerService.findPage(userId, pageNo, pageSize, audit, searchKey);
    }

    /**
     * 店铺审核
     *
     * @param audit  AUDITING 待审核/审核中
     *               NOT_PASS_AUDIT 审核未通过
     *               PASS_AUDIT 审核通过
     * @param reason
     * @param id
     * @return
     */
    @PostMapping("/audit")
    public DataRet<String> audit(@RequestParam(value = "audit") String audit,
                                 @RequestParam(value = "id") Long id,
                                 @RequestParam(value = "reason", required = false) String reason) {
        return sellerService.audit(audit, reason, id);
    }
}
