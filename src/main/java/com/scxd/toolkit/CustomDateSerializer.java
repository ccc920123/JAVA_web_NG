package com.scxd.toolkit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * json序列化时间
 * 用法：然后在你的POJO上找到日期的get方法
 *
 * @JsonSerialize(using = CustomDateSerializer.class)
 * public Date getCreateAt() {
 * return createAt;
 * }
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (value == null || value.equals("null")) {
            jgen.writeString("--");
        } else {
            String formattedDate = formatter.format(value);
            jgen.writeString(formattedDate);
        }
    }
}