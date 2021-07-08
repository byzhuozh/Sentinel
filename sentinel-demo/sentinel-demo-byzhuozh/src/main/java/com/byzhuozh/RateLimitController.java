package com.byzhuozh;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkhsh
 */
@RestController
public class RateLimitController {

    @GetMapping("byResource")
    @SentinelResource(value = "byResource", blockHandler = "handlerException")
    public CommonResult<Payment> byResource() {
        return new CommonResult<>(200, "按资源名称限流测试ok", new Payment(2020L, "serial001"));
    }

    public CommonResult<Payment> handlerException(BlockException exception) {
        return new CommonResult<>(444, exception.getMessage() + ": 服务不可用");
    }

    @GetMapping("rateLimit/byUrl")
    @SentinelResource("byUrl")
    public CommonResult<Payment> byUrl() {
        return new CommonResult<>(200, "按 URL 限流测试ok", new Payment(2030L, "serial002"));
    }

    @GetMapping("rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler", blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException")
    public CommonResult<Payment> customerBlockHandler() {
        return new CommonResult<>(200, "按客户自定义规则测试ok", new Payment(2030L, "serial003"));
    }
}
