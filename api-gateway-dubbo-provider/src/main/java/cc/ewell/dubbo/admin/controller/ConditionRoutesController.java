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

import org.apache.commons.lang3.StringUtils;

import cc.ewell.dubbo.admin.annotation.Authority;
import cc.ewell.dubbo.admin.common.exception.ParamValidationException;
import cc.ewell.dubbo.admin.common.exception.ResourceNotFoundException;
import cc.ewell.dubbo.admin.common.exception.VersionValidationException;
import cc.ewell.dubbo.admin.model.dto.ConditionRouteDTO;
import cc.ewell.dubbo.admin.service.ConsumerService;
import cc.ewell.dubbo.admin.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@Authority(needLogin = true)
@RestController
@RequestMapping("/api/{env}/rules/route/condition")
public class ConditionRoutesController {

    private final RouteService routeService;
    private final ConsumerService consumerService;

    @Autowired
    public ConditionRoutesController(RouteService routeService, ConsumerService consumerService) {
        this.routeService = routeService;
        this.consumerService = consumerService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createRule(@RequestBody ConditionRouteDTO routeDTO, @PathVariable String env) {
        String serviceName = routeDTO.getService();
        String app = routeDTO.getApplication();
        if (StringUtils.isEmpty(serviceName) && StringUtils.isEmpty(app)) {
            throw new ParamValidationException("serviceName and app is Empty!");
        }
        if (StringUtils.isNotEmpty(app) && consumerService.findVersionInApplication(app).equals("2.6")) {
            throw new VersionValidationException("dubbo 2.6 does not support application scope routing rule");
        }
        routeService.createConditionRoute(routeDTO);
        return true;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean updateRule(@PathVariable String id, @RequestBody ConditionRouteDTO newConditionRoute, @PathVariable String env) {
        ConditionRouteDTO oldConditionRoute = routeService.findConditionRoute(id);
        if (oldConditionRoute == null) {
            throw new ResourceNotFoundException("can not find route rule for: " + id);
        }
        routeService.updateConditionRoute(newConditionRoute);
        return true;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ConditionRouteDTO> searchRoutes(@RequestParam(required = false) String application,
                                                      @RequestParam(required = false) String service, @PathVariable String env,
                                                      @RequestParam(required = false) String serviceVersion,
                                                      @RequestParam(required = false) String serviceGroup) {
        ConditionRouteDTO conditionRoute;
        List<ConditionRouteDTO> result = new ArrayList<>();
        ConditionRouteDTO crDTO = new ConditionRouteDTO();
        if (StringUtils.isNotBlank(application)) {
            crDTO.setApplication(application);
            conditionRoute = routeService.findConditionRoute(crDTO);
        } else if (StringUtils.isNotBlank(service)) {
            crDTO.setService(service);
            crDTO.setServiceVersion(serviceVersion);
            crDTO.setServiceGroup(serviceGroup);
            conditionRoute = routeService.findConditionRoute(crDTO);
        } else {
            throw new ParamValidationException("Either Service or application is required.");
        }
        if (conditionRoute != null && conditionRoute.getConditions() != null) {
            result.add(conditionRoute);
        }
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ConditionRouteDTO detailRoute(@PathVariable String id, @PathVariable String env) {
        ConditionRouteDTO conditionRoute = routeService.findConditionRoute(id);
        if (conditionRoute == null || conditionRoute.getConditions() == null) {
            throw new ResourceNotFoundException("Unknown ID!");
        }
        return conditionRoute;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteRoute(@PathVariable String id, @PathVariable String env) {
        routeService.deleteConditionRoute(id);
        return true;
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.PUT)
    public boolean enableRoute(@PathVariable String id) {
        routeService.enableConditionRoute(id);
        return true;
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.PUT)
    public boolean disableRoute(@PathVariable String id, @PathVariable String env) {
        routeService.disableConditionRoute(id);
        return true;
    }

}