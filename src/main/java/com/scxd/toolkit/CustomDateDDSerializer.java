package com.scxd.toolkit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:50 2018/11/16
 * @Modified By:
 *  @JsonSerialize(using = CustomDateDDSerializer.class)
 * public Date getCreateAt() {
 * return createAt;
 * }
 */
public class CustomDateDDSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (value == null || value.equals("null")) {
            jgen.writeString("--");
        } else {
            String formattedDate = formatter.format(value);
            jgen.writeString(formattedDate);
        }
    }
}
