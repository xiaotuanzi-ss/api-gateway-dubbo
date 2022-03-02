package cc.ewell.example.service.consumer.impl;

import cc.ewell.authority.api.v3.dto.req.UserReq;
import cc.ewell.authority.api.v3.dto.res.UserEntityRes;
import cc.ewell.authority.api.v3.service.IUserService;
import cc.ewell.example.service.consumer.DemoAuthConsumerService;
import co.faao.plugin.transmission.request.MessageHeader;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
@Service
public class DemoAuthConsumerServiceImpl implements DemoAuthConsumerService {
	
	@DubboReference(version = "3.0.0")
	IUserService userService;
	
	@Override
	public UserEntityRes getUserDetailByStaffCode(String staffCode) {
		UserReq req = new UserReq();
		req.setStaffCode(staffCode);
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setParams(JSON.toJSONString(req));
		return (UserEntityRes) userService.getUserDetailByStaffCode(messageHeader).getObject();
	}
}
