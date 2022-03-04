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

import cc.ewell.dubbo.admin.service.impl.AbstractService;
import cc.ewell.dubbo.admin.common.util.Constants;
import cc.ewell.dubbo.admin.common.util.ConvertUtil;
import cc.ewell.dubbo.admin.common.util.YamlParser;
import cc.ewell.dubbo.admin.model.dto.MeshRouteDTO;
import cc.ewell.dubbo.admin.model.store.mesh.destination.DestinationRule;
import cc.ewell.dubbo.admin.model.store.mesh.virtualservice.VirtualServiceRule;
import cc.ewell.dubbo.admin.service.MeshRouteService;

import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class MeshRouteServiceImpl extends AbstractService implements MeshRouteService {

    @Override
    public boolean createMeshRule(MeshRouteDTO meshRoute) {
        String routeRule = meshRoute.getMeshRule();
        checkMeshRule(routeRule);
        String id = ConvertUtil.getIdFromDTO(meshRoute);
        String path = getPath(id);
        dynamicConfiguration.setConfig(path, routeRule);
        return true;
    }

    @Override
    public boolean updateMeshRule(MeshRouteDTO meshRoute) {
        String id = ConvertUtil.getIdFromDTO(meshRoute);
        String path = getPath(id);
        checkMeshRule(meshRoute.getMeshRule());
        dynamicConfiguration.setConfig(path, meshRoute.getMeshRule());
        return true;
    }

    private void checkMeshRule(String meshRule) {
        Iterable<Object> objectIterable = YamlParser.loadAll(meshRule);
        for (Object result : objectIterable) {
            Map resultMap = (Map) result;
            if ("DestinationRule".equals(resultMap.get("kind"))) {
                YamlParser.loadObject(YamlParser.dumpObject(result), DestinationRule.class);
            } else if ("VirtualService".equals(resultMap.get("kind"))) {
                YamlParser.loadObject(YamlParser.dumpObject(result), VirtualServiceRule.class);
            }
        }
    }

    @Override
    public boolean deleteMeshRule(String id) {
        String path = getPath(id);
        return dynamicConfiguration.deleteConfig(path);
    }

    @Override
    public MeshRouteDTO findMeshRoute(String id) {
        String path = getPath(id);
        String rule = dynamicConfiguration.getConfig(path);
        if (rule == null) {
            return null;
        }
        MeshRouteDTO meshRoute = new MeshRouteDTO();
        meshRoute.setApplication(id);
        meshRoute.setMeshRule(rule);
        return meshRoute;
    }

    private String getPath(String id) {
        return id + Constants.MESH_RULE_SUFFIX;
    }

}
