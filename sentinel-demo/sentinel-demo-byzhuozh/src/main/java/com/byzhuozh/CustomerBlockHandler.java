package com.byzhuozh;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author lkhsh
 */
public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(444,"按客户自定义，全局 handlerException ------ 1");
    }
    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(444,"按客户自定义，全局 handlerException ------ 2");
    }
}
