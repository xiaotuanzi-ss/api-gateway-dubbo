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
import co.faao.plugin.SpeedException;
import org.apache.dubbo.common.URL;
import org.apache.zookeeper.*;

public class ZookeeperTest implements RegistryTest {

    @Override
    public void init(URL url) {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(url.getAddress(), 10000, watchedEvent -> {
            });
            zk.exists("/test", false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpeedException("DUBBO001," + e.getMessage());
        } finally {
            if (zk != null) {
                try {
                    zk.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
