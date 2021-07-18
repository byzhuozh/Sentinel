package com.byzhuozh;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuozh
 * @version : FlowRuleTest.java, v 0.1 2021/7/10 13:20 zhuozh Exp $
 */
public class FlowRuleTest {

    public static void main(String[] args) {
        int i = 0;
        initFlowRules();
        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("HelloWorld");
                i++;
                /*您的业务逻辑 - 开始*/
                System.out.println("hello world" + i);
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e1) {
                /*流控逻辑处理 - 开始*/
                i++;
                System.out.println("block!" + i);

                if (i > 25) {
                    return;
                }
                /*流控逻辑处理 - 结束*/
            } finally {
                if (entry != null) {
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(20);
        rule.setLimitApp("test");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
