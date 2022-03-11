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

package cc.ewell.dubbo.admin.registry.test.impl;

import cc.ewell.dubbo.admin.registry.test.RegistryTest;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.common.utils.StringConstantFieldValuePredicate;

import java.util.Map;
import java.util.Properties;

import static com.alibaba.nacos.api.PropertyKeyConst.NAMESPACE;
import static com.alibaba.nacos.api.PropertyKeyConst.SERVER_ADDR;

public class NacosTest implements RegistryTest {
    private static final Logger logger = LoggerFactory.getLogger(NacosTest.class);

    @Override
    public void init(URL url) {
        buildConfigService(url);
    }

    private void buildConfigService(URL url) {
        Properties nacosProperties = buildNacosProperties(url);
        try {
            ConfigService configService = NacosFactory.createConfigService(nacosProperties);
            System.out.println(configService.getServerStatus());
        } catch (NacosException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getErrMsg(), e);
            }
            throw new IllegalStateException(e);
        }
    }

    private Properties buildNacosProperties(URL url) {
        Properties properties = new Properties();
        setServerAddr(url, properties);
        setNamespace(url, properties);
        Map<String, String> parameters = url.getParameters(
                StringConstantFieldValuePredicate.of(PropertyKeyConst.class));
        properties.putAll(parameters);
        return properties;
    }

    private void setServerAddr(URL url, Properties properties) {

        String serverAddr = url.getHost() + // Host
                ":" +
                url.getPort() // Port
                ;
        properties.put(SERVER_ADDR, serverAddr);
    }

    private void setNamespace(URL url, Properties properties) {
        String namespace = url.getParameter(NAMESPACE);
        if (StringUtils.isNotBlank(namespace)) {
            properties.put(NAMESPACE, namespace);
        }
    }

}
