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

package cc.ewell.dubbo.admin.controller;

import com.google.gson.Gson;
import cc.ewell.dubbo.admin.common.util.Constants;

import cc.ewell.dubbo.admin.annotation.Authority;
import cc.ewell.dubbo.admin.common.util.ConvertUtil;
import cc.ewell.dubbo.admin.common.util.ServiceTestUtil;
import cc.ewell.dubbo.admin.common.util.ServiceTestV3Util;
import cc.ewell.dubbo.admin.model.domain.MethodMetadata;
import cc.ewell.dubbo.admin.model.dto.ServiceTestDTO;
import cc.ewell.dubbo.admin.service.ProviderService;
import cc.ewell.dubbo.admin.service.impl.GenericServiceImpl;
import org.apache.dubbo.metadata.definition.model.FullServiceDefinition;
import org.apache.dubbo.metadata.definition.model.MethodDefinition;
import org.apache.dubbo.metadata.report.identifier.MetadataIdentifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@Authority(needLogin = false)
@RestController
@RequestMapping("/api/{env}/test")
public class ServiceTestController {
    private final GenericServiceImpl genericService;
    private final ProviderService providerService;

    public ServiceTestController(GenericServiceImpl genericService, ProviderService providerService) {
        this.genericService = genericService;
        this.providerService = providerService;
    }



    @RequestMapping(method = RequestMethod.POST)
    public Object test(@PathVariable String env, @RequestBody ServiceTestDTO serviceTestDTO) {
        try {
            return genericService.invoke(serviceTestDTO.getService(), serviceTestDTO.getMethod(), serviceTestDTO.getParameterTypes(), serviceTestDTO.getParams());
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/method", method = RequestMethod.GET)
    public MethodMetadata methodDetail(@PathVariable String env, @RequestParam String application, @RequestParam String service,
                                       @RequestParam String method) {
        Map<String, String> info = ConvertUtil.serviceName2Map(service);
        MetadataIdentifier identifier = new MetadataIdentifier(info.get(Constants.INTERFACE_KEY),
                info.get(Constants.VERSION_KEY),
                info.get(Constants.GROUP_KEY), Constants.PROVIDER_SIDE, application);
        String metadata = providerService.getProviderMetaData(identifier);
        MethodMetadata methodMetadata = null;
        if (metadata != null) {
            Gson gson = new Gson();
            String release = providerService.findVersionInApplication(application);
            if (release.startsWith("2.")) {
                cc.ewell.dubbo.admin.model.domain.FullServiceDefinition serviceDefinition = gson.fromJson(metadata,
                        cc.ewell.dubbo.admin.model.domain.FullServiceDefinition.class);
                List<cc.ewell.dubbo.admin.model.domain.MethodDefinition> methods = serviceDefinition.getMethods();
                if (methods != null) {
                    for (cc.ewell.dubbo.admin.model.domain.MethodDefinition m : methods) {
                        if (ServiceTestUtil.sameMethod(m, method)) {
                            methodMetadata = ServiceTestUtil.generateMethodMeta(serviceDefinition, m);
                            break;
                        }
                    }
                }
            } else {
                FullServiceDefinition serviceDefinition = gson.fromJson(metadata,
                        FullServiceDefinition.class);
                List<MethodDefinition> methods = serviceDefinition.getMethods();
                if (methods != null) {
                    for (MethodDefinition m : methods) {
                        if (ServiceTestV3Util.sameMethod(m, method)) {
                            methodMetadata = ServiceTestV3Util.generateMethodMeta(serviceDefinition, m);
                            break;
                        }
                    }
                }
            }
        }
        return methodMetadata;
    }
}
