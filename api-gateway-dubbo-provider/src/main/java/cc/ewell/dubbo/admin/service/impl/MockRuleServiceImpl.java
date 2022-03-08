/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cc.ewell.dubbo.admin.service.impl;

import cc.ewell.dubbo.admin.mapper.MockRuleMapper;
import cc.ewell.dubbo.admin.model.domain.MockRule;
import cc.ewell.dubbo.admin.model.dto.MockRuleDTO;
import cc.ewell.dubbo.admin.service.MockRuleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.mock.api.MockContext;
import org.apache.dubbo.mock.api.MockResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The implement of {@link MockRuleService}.
 */
@Component
public class MockRuleServiceImpl implements MockRuleService {

    @Autowired
    private MockRuleMapper mockRuleMapper;

    @Override
    public void createOrUpdateMockRule(MockRuleDTO mockRule) {
        if (Objects.isNull(mockRule.getServiceName()) || Objects.isNull(mockRule.getMethodName())
                || Objects.isNull(mockRule.getRule())) {
            throw new IllegalStateException("Param serviceName, methodName, rule cannot be null");
        }
        MockRule rule = MockRule.toMockRule(mockRule);
        rule.setServiceName(mockRule.getServiceName());
        rule.setMethodName(mockRule.getMethodName());
        MockRule existRule = mockRuleMapper.selectOne(rule);

        // check if we can save or update the rule, we need keep the serviceName + methodName is unique.
        if (Objects.nonNull(existRule)) {
            if (Objects.equals(rule.getServiceName(), existRule.getServiceName())
                    && Objects.equals(rule.getMethodName(), existRule.getMethodName())) {
                if (!Objects.equals(rule.getId(), existRule.getId())) {
                    throw new DuplicateKeyException("Service Name and Method Name must be unique");
                }
            }
        }

        if (Objects.nonNull(rule.getId())) {
            mockRuleMapper.updateByPrimaryKeySelective(rule);
            return;
        }
        mockRuleMapper.insert(rule);
    }

    @Override
    public void deleteMockRuleById(Long id) {
        MockRule mockRule = mockRuleMapper.selectByPrimaryKey(id);
        if (Objects.isNull(mockRule)) {
            throw new IllegalStateException("Mock Rule cannot find");
        }
        mockRuleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Page<MockRuleDTO> listMockRulesByPage(String filter, Pageable pageable) {
        MockRule mockRule = new MockRule();
        mockRule.setServiceName(filter);
        List<MockRule> mockRules = mockRuleMapper.select(mockRule);
        int total = mockRules.size();
        final List<MockRuleDTO> content = mockRules.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(MockRuleDTO::toMockRuleDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public MockResult getMockData(MockContext mockContext) {
//        MockRule mockRule = new MockRule();
//        mockRule.setMethodName(mockContext.getMethodName());
//        mockRule.setServiceName(mockContext.getServiceName());
//        mockRule = mockRuleMapper.selectOne(mockRule);
        MockResult mockResult = new MockResult();
//        if (Objects.isNull(mockRule)) {
//            return mockResult;
//        }
//        mockResult.setEnable(mockRule.getEnable());
//        mockResult.setContent(mockRule.getRule());
        return mockResult;
    }
}
