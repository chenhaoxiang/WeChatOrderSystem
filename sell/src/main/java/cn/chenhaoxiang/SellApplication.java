package cn.chenhaoxiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@MapperScan(basePackages = {"cn.chenhaoxiang.dataObject.mapper"})//配置mybatis mapper扫描路径
@EnableCaching //缓存支持  配置Redis缓存需要的
public class SellApplication {
	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}
}
