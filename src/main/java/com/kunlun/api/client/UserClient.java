package com.kunlun.api.client;

import com.kunlun.api.hystrix.UserClientHystrix;
import com.kunlun.result.DataRet;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-10 15:04
 */
@FeignClient(value = "cloud-service-user-center", fallback = UserClientHystrix.class)
public interface UserClient {


    /**
     * 校验用户类型
     *
     * @param userId Long
     * @return DataRet
     */
    @GetMapping("/user/validAdmin")
    DataRet validAdmin(@RequestParam("userId") Long userId);
}
