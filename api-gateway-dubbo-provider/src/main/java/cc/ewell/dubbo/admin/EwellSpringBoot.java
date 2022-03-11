package cc.ewell.dubbo.admin;

import co.faao.plugin.starter.actuator.actuatorsecurity.annotation.EwellActuator;
import co.faao.plugin.starter.apollo.config.ConfigApType;
import co.faao.plugin.starter.flyway.annotation.EwellFlyway;
import co.faao.plugin.starter.jaeger.annotation.EwellJaeger;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import co.faao.plugin.EwellStarter;
import co.faao.plugin.starter.data.comments.EwellData;
import co.faao.plugin.starter.dubbo.comments.EwellDubbo;
import co.faao.plugin.starter.mybatis.comments.EwellMybatis;
import co.faao.plugin.starter.dubbo.other.EwellDubboType;
import co.faao.plugin.starter.swagger.comments.EwellSwagger;
import co.faao.plugin.starter.mybatis.other.MybatisFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author Infra
 * @date 2022-01-04
 * @description
 */
@EwellStarter(ConfigSources = ConfigApType.class)
@EwellSwagger(sw = "on")
@EwellData(sw = "on")
@EwellMybatis(Filter = MybatisFilter.TkFilter)
@EwellDubbo(Type = EwellDubboType.class)
@EwellJaeger(sw = "on")
@EwellActuator(sw = "on")
@EwellFlyway(sw = "on")
@SpringBootApplication(exclude={
		HibernateJpaAutoConfiguration.class
})
@EnableDubbo(scanBasePackages = {"cc.ewell.dubbo.admin"})
@MapperScan(basePackages = {"cc.ewell.dubbo.admin.mapper"})
public class EwellSpringBoot {
	public static void main(String[] args) {
		SpringApplication.run(EwellSpringBoot.class, args);
	}




}
