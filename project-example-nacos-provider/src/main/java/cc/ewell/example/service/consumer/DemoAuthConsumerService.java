package cc.ewell.example.service.consumer;

import cc.ewell.authority.api.v3.dto.res.UserEntityRes;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
public interface DemoAuthConsumerService {
	UserEntityRes getUserDetailByStaffCode(String staffCode);
}
