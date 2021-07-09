/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.slots;

import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slotchain.*;
import com.alibaba.csp.sentinel.spi.Spi;
import com.alibaba.csp.sentinel.spi.SpiLoader;

import java.util.List;

/**
 * Builder for a default {@link ProcessorSlotChain}.
 *
 * @author qinan.qn
 * @author leyou
 */
@Spi(isDefault = true)
public class DefaultSlotChainBuilder implements SlotChainBuilder {

    @Override
    public ProcessorSlotChain build() {
        // 处理器链表
        ProcessorSlotChain chain = new DefaultProcessorSlotChain();

        // spi 加载 ProcessorSlot 的实现类
        // 位置: META-INF/services/com.alibaba.csp.sentinel.slotchain.ProcessorSlot
        // 实现类:
        //      NodeSelectorSlot
        //      ClusterBuilderSlot
        //      LogSlot
        //      StatisticSlot
        //      AuthoritySlot
        //      SystemSlot
        //      FlowSlot
        //      DegradeSlot
        List<ProcessorSlot> sortedSlotList = SpiLoader.of(ProcessorSlot.class).loadInstanceListSorted();

        for (ProcessorSlot slot : sortedSlotList) {
            //非 AbstractLinkedProcessorSlot 类型的处理器跳过
            if (!(slot instanceof AbstractLinkedProcessorSlot)) {
                RecordLog.warn("The ProcessorSlot(" + slot.getClass().getCanonicalName() + ") is not an instance of AbstractLinkedProcessorSlot, can't be added into ProcessorSlotChain");
                continue;
            }

            chain.addLast((AbstractLinkedProcessorSlot<?>) slot);
        }

        return chain;
    }
}
