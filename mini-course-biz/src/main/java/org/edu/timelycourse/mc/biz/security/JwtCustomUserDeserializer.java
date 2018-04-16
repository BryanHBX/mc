package org.edu.timelycourse.mc.biz.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by x36zhao on 2018/4/16.
 */
public class JwtCustomUserDeserializer extends JsonDeserializer
{
    @Override
    public Object deserialize (JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException
    {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);

        List<JwtAuthorityName> authorities = mapper.convertValue(jsonNode.get("authorities"),
                new TypeReference<List<JwtAuthorityName>>() {
        });

        JwtUser result = JwtUserFactory.create(
                readJsonNode(jsonNode, "uid").asInt(),
                readJsonNode(jsonNode, "sid").asInt(),
                readJsonNode(jsonNode, "username").asText(),
                readJsonNode(jsonNode, "idCard").asText(),
                readJsonNode(jsonNode, "phone").asText(),
                authorities.stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                        .collect(Collectors.toList())
        );

        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field)
    {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }
}
