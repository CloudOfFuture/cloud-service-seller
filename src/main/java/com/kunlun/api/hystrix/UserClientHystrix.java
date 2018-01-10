package com.kunlun.api.hystrix;

import com.kunlun.api.client.UserClient;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Component;

/**
 * @author ycj
 * @version V1.0 <>
 * @date 2018-01-10 15:05
 */
@Component
public class UserClientHystrix implements UserClient {
    @Override
    public DataRet validAdmin(Long userId) {
        return new DataRet("ERROR", "校验请求失败");
    }
}
