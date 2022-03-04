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

package cc.ewell.dubbo.admin.registry.mapping;

import cc.ewell.dubbo.admin.registry.mapping.AddressChangeListener;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.client.ServiceDiscovery;
import org.apache.dubbo.registry.client.event.listener.ServiceInstancesChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdminServiceInstancesChangedListener extends ServiceInstancesChangedListener {

    private AddressChangeListener addressChangeListener;

    private Map<String, Object> oldServiceUrls;

    public AdminServiceInstancesChangedListener(Set<String> serviceNames, ServiceDiscovery serviceDiscovery, AddressChangeListener addressChangeListener) {
        super(serviceNames, serviceDiscovery);
        this.addressChangeListener = addressChangeListener;
        oldServiceUrls = new HashMap<>();
    }

    protected void notifyAddressChanged() {
        oldServiceUrls.keySet().stream()
                .filter(protocolServiceKey -> !serviceUrls.containsKey(protocolServiceKey))
                .forEach(protocolServiceKey -> addressChangeListener.notifyAddressChanged(protocolServiceKey, new ArrayList<>()));
        serviceUrls.forEach((protocolServiceKey, urls) -> addressChangeListener.notifyAddressChanged(protocolServiceKey, (List<URL>) urls));

        oldServiceUrls = serviceUrls;
    }
}
