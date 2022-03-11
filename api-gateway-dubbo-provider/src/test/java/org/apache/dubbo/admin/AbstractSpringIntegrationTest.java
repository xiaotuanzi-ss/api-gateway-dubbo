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

package org.apache.dubbo.admin;

import cc.ewell.dubbo.admin.EwellSpringBoot;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.util.SocketUtils;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EwellSpringBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = EwellSpringBoot.class, initializers = AbstractSpringIntegrationTest.PropertyOverrideContextInitializer.class)
public abstract class AbstractSpringIntegrationTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    protected static final TestingServer zkServer;
    protected static final CuratorFramework zkClient;

    static {
        try {
            int zkPort = SocketUtils.findAvailableTcpPort();
            zkServer = new TestingServer(zkPort, true);
            zkClient = CuratorFrameworkFactory.newClient(zkServer.getConnectString(), new RetryOneTime(2000));
            zkClient.start();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @LocalServerPort
    protected int port;

    protected String url(final String path) {
        return "http://localhost:" + port + path;
    }

    public static class PropertyOverrideContextInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static final String PROPERTY_FIRST_VALUE = "contextClass";

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    "admin.registry.address=zookeeper://" + zkServer.getConnectString());
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    "admin.metadata-report.address=zookeeper://" + zkServer.getConnectString());
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    "admin.config-center=zookeeper://" + zkServer.getConnectString());
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                    "dubbo.config.ignore-duplicated-interface=true");
        }
    }
}
