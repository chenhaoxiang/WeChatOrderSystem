package cn.chenhaoxiang.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 陈浩翔.
 * Date: 2018/1/16.
 * Time: 下午 8:14.
 * Explain: 让时间戳精度在秒
 */
public class Date2LongSerializer extends JsonSerializer<Date> {//Date类型对应OrderDTO中的时间的属性类型
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(date.getTime() / 1000);//这个date是加了JsonSerialize注解的参数
    }
}
