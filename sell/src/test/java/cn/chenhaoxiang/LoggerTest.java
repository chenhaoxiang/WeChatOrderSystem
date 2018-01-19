package cn.chenhaoxiang;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/8.
 * Time: 下午 8:05.
 * Explain:日志测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    /**
     * 传统方式实现日志
     */
    @Test
    public void test1(){
        logger.debug("debug");//默认日志级别为info
        logger.info("info");
        logger.error("error");
        logger.warn("warn");
    }

    /**
     * Slf4j注解方式实现日志
     */
    @Test
    public void test2(){
        log.debug("debug");//默认日志级别为info
        log.info("info");
        log.error("error");
        log.warn("warn");
        String name = "陈浩翔";
        String password = "123456";
        log.info("name:{},password:{}",name,password);//{}表示一个占位符
    }

}
