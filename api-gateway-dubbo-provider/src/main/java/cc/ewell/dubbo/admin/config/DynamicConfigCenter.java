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

package cc.ewell.dubbo.admin.config;

import cc.ewell.dubbo.admin.common.exception.ConfigurationException;
import cc.ewell.dubbo.admin.common.util.Constants;
import cc.ewell.dubbo.admin.registry.config.GovernanceConfiguration;
import cc.ewell.dubbo.admin.registry.mapping.AdminMappingListener;
import cc.ewell.dubbo.admin.registry.mapping.ServiceMapping;
import cc.ewell.dubbo.admin.registry.mapping.impl.NoOpServiceMapping;
import cc.ewell.dubbo.admin.registry.metadata.MetaDataCollector;
import cc.ewell.dubbo.admin.registry.metadata.impl.NoOpMetadataCollector;
import cc.ewell.dubbo.admin.service.impl.InstanceRegistryCache;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.metadata.MappingListener;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.registry.RegistryService;
import org.apache.dubbo.registry.client.ServiceDiscovery;
import org.apache.dubbo.registry.client.ServiceDiscoveryFactory;

import java.util.Arrays;

import static org.apache.dubbo.common.constants.CommonConstants.CLUSTER_KEY;
import static org.apache.dubbo.registry.client.ServiceDiscoveryFactory.getExtension;

/**
 * @author xushiling
 * @description 动态切换注册中心
 * @createDate 2022/3/11 10:36 上午
 */
//@Configuration
public class DynamicConfigCenter {

    //centers in dubbo 2.7
//    @Value("${admin.config-center:}")
//    private String configCenter;
//
//    //    @Value("${infra.dubbo.registryaddress:}")
    private String registryAddress;
//
//    //    @Value("${admin.metadata-report.address:}")
//    private String metadataAddress;
//
//    //    @Value("${admin.metadata-report.cluster:false}")
//    private boolean cluster;
//
//    //    @Value("${admin.registry.group:}")
//    private String registryGroup;
//
//    //    @Value("${admin.config-center.group:}")
//    private String configCenterGroup;
//
//    //    @Value("${admin.metadata-report.group:}")
//    private String metadataGroup;
//
//    //    @Value("${admin.registry.namespace:}")
//    private String registryNameSpace;
//
//    //    @Value("${admin.config-center.namespace:}")
//    private String configCenterGroupNameSpace;
//
//    //    @Value("${admin.metadata-report.namespace:}")
//    private String metadataGroupNameSpace;
//
//    //    @Value("${admin.config-center.username:}")
//    private String username;
//    //    @Value("${admin.config-center.password:}")
//    private String password;

    public DynamicConfigCenter(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    private static final Logger logger = LoggerFactory.getLogger(DynamicConfigCenter.class);

    //    private URL configCenterUrl;
    private URL registryUrl;//配置中心，注册中心使用同一个地址
//    private URL metadataUrl;

    /*
     * generate dynamic configuration client
     */
//    @Bean("governanceConfiguration")
    public GovernanceConfiguration getDynamicConfiguration() {
        GovernanceConfiguration dynamicConfiguration = null;

        if (StringUtils.isNotEmpty(registryAddress)) {
            registryUrl = formUrl(registryAddress);
            dynamicConfiguration = ExtensionLoader.getExtensionLoader(GovernanceConfiguration.class).getDefaultExtension();
            dynamicConfiguration.setUrl(registryUrl);
            dynamicConfiguration.init();
            String config = dynamicConfiguration.getConfig(Constants.DUBBO_PROPERTY);

            if (StringUtils.isNotEmpty(config)) {
                Arrays.stream(config.split("\n")).forEach(s -> {
                    if (s.startsWith(Constants.REGISTRY_ADDRESS)) {
                        String registryAddress = removerConfigKey(s);
                        registryUrl = formUrl(registryAddress);
                    } else if (s.startsWith(Constants.METADATA_ADDRESS)) {
                        registryUrl = formUrl(registryAddress);
                    }
                });
            }
        }
        if (dynamicConfiguration == null) {
            if (StringUtils.isNotEmpty(registryAddress)) {
                registryUrl = formUrl(registryAddress);
                dynamicConfiguration = ExtensionLoader.getExtensionLoader(GovernanceConfiguration.class).getDefaultExtension();
                dynamicConfiguration.setUrl(registryUrl);
                dynamicConfiguration.init();
                logger.warn("you are using dubbo.registry.address, which is not recommend, please refer to: https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
            } else {
                throw new ConfigurationException("Either config center or registry address is needed, please refer to https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
                //throw exception
            }
        }
        return dynamicConfiguration;
    }

    /*
     * generate registry client
     */
//    @Bean("dubboRegistry")
//    @DependsOn("governanceConfiguration")
    public Registry getRegistry() {
        Registry registry = null;
        if (registryUrl == null) {
            if (StringUtils.isBlank(registryAddress)) {
                throw new ConfigurationException("Either config center or registry address is needed, please refer to https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
            }
            registryUrl = formUrl(registryAddress);
        }
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        registry = registryFactory.getRegistry(registryUrl);
        return registry;
    }

    /*
     * generate metadata client
     */
//    @Bean("metaDataCollector")
//    @DependsOn("governanceConfiguration")
    public MetaDataCollector getMetadataCollector() {
        MetaDataCollector metaDataCollector = new NoOpMetadataCollector();
        if (registryUrl == null) {
            if (StringUtils.isNotEmpty(registryAddress)) {
                registryUrl = formUrl(registryAddress);
                registryUrl = registryUrl.addParameter(CLUSTER_KEY, false);
            }
        }
        if (registryUrl != null) {
            metaDataCollector = ExtensionLoader.getExtensionLoader(MetaDataCollector.class).getExtension(registryUrl.getProtocol());
            metaDataCollector.setUrl(registryUrl);
            metaDataCollector.init();
        } else {
            logger.warn("you are using dubbo.registry.address, which is not recommend, please refer to: https://github.com/apache/incubator-dubbo-admin/wiki/Dubbo-Admin-configuration");
        }
        return metaDataCollector;
    }


    //    @Bean(destroyMethod = "destroy")
//    @DependsOn("dubboRegistry")
    public ServiceDiscovery getServiceDiscoveryRegistry() throws Exception {
        URL registryURL = registryUrl.setPath(RegistryService.class.getName());
        ServiceDiscoveryFactory factory = getExtension(registryURL);
        ServiceDiscovery serviceDiscovery = factory.getServiceDiscovery(registryURL);
        serviceDiscovery.initialize(registryURL);
        return serviceDiscovery;
    }

    //    @Bean
//    @DependsOn("metaDataCollector")
    public ServiceMapping getServiceMapping(ServiceDiscovery serviceDiscovery, InstanceRegistryCache instanceRegistryCache) {
        ServiceMapping serviceMapping = new NoOpServiceMapping();
        if (registryUrl == null) {
            return serviceMapping;
        }
        MappingListener mappingListener = new AdminMappingListener(serviceDiscovery, instanceRegistryCache);
        serviceMapping = ExtensionLoader.getExtensionLoader(ServiceMapping.class).getExtension(registryUrl.getProtocol());
        serviceMapping.addMappingListener(mappingListener);
        serviceMapping.init(registryUrl);
        return serviceMapping;
    }

    public static String removerConfigKey(String properties) {
        String[] split = properties.split("=");
        String[] address = new String[split.length - 1];
        System.arraycopy(split, 1, address, 0, split.length - 1);
        return String.join("=", address).trim();
    }

    private URL formUrl(String config) {
        URL url = URL.valueOf(config);
        return url;
    }
}
