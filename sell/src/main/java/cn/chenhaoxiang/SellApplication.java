package cn.chenhaoxiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = {"cn.chenhaoxiang.dataObject.mapper"})//配置mybatis mapper扫描路径
public class SellApplication {
	public static void main(String[] args) {
		SpringApplication.run(SellApplication.class, args);
	}
}
