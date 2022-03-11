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


import cc.ewell.dubbo.admin.registry.metadata.MetaDataCollector;
import cc.ewell.dubbo.admin.registry.test.RegistryTest;
import co.faao.plugin.SpeedException;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.metadata.report.identifier.KeyTypeEnum;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

import static org.apache.dubbo.common.constants.CommonConstants.*;
import static org.apache.dubbo.metadata.MetadataConstants.META_DATA_STORE_TAG;

public class RedisTest implements RegistryTest {

    @Override
    public void init(URL url) {
        Jedis jedis = null;
        try {
            // 比较特殊的是，redis连接池的配置首先要创建一个连接池配置对象
            JedisPoolConfig config = new JedisPoolConfig();
            // 创建Jedis连接池对象
            JedisPool jedisPool = new JedisPool(config, url.getHost(), url.getPort(), 1000, url.getPassword(), 0);
            // 获取连接
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpeedException("DUBBO001," + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}
