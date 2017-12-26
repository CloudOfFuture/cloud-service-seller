package com.kunlun.api.service.impl;

import com.kunlun.api.service.IndexService;
import com.kunlun.result.DataRet;
import org.springframework.stereotype.Service;

/**
 * @author by kunlun
 * @version <0.1>
 * @created on 2017/12/25.
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Override
    public DataRet<String> test() {
        return new DataRet<>("成功");
    }
}
