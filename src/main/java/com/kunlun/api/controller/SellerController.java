package com.kunlun.api.controller;

import com.kunlun.api.service.SellerService;
import com.kunlun.entity.Store;
import com.kunlun.result.DataRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author by hmy
 * @version <0.1>
 * @created on 2017-12-26.
 */
@RestController
@RequestMapping("seller")
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
    public DataRet<String> add(@RequestBody Store store) {
        return sellerService.add(store);
    }

    /**
     * 修改店铺状态
     *
     * @param id
     * @param status 店铺状态 NORMAL 正常 ,
     *               CLOSE_LEADER 管理员关闭,
     *               CLOSE 关闭，
     *               DELETE 删除状态
     *@param operator
     * @return
     */
    @PostMapping("/updateStatus")
    public DataRet<String> updateStatus(@RequestParam(value = "id") Long id,
                                        @RequestParam(value = "status") String status,
                                        @RequestParam(value = "operator") String operator) {
        return sellerService.updateStatus(id, status,operator);
    }

    /**
     * 修改店铺信息
     *
     * @param store
     * @return
     */
    @PostMapping("/modify")
    public DataRet<String> modify(@RequestBody Store store) {
        return sellerService.update(store);
    }

    /**
     * 查询店铺详情
     *
     * @param userId
     * @return
     */

    @GetMapping("/findByUserId")
    public DataRet<Store> findByUserId(@RequestParam(value = "userId") Long userId) {
        return sellerService.findByUserId(userId);
    }
}
