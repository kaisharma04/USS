package com.kai2code.USS.service;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UrlShortnerService {

    @Autowired
    StringRedisTemplate redisTemplate;

    public String getUrlByHash(String id){
        String url = redisTemplate.opsForValue().get(id);
        System.out.println(url);
        if(url != null){
            return url;
        }
        throw new RuntimeException("No url present for this id:"+id);
    }

    public String ShortThatUrl(String url){
        UrlValidator validator = new UrlValidator(new String[]{"http","https"},UrlValidator.ALLOW_LOCAL_URLS);

        if(validator.isValid(url)){
           String id =  Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).toString();
           System.out.println(id);

           redisTemplate.opsForValue().set(id,url);
           return id;
        }
        throw new RuntimeException("Invalid url:"+url);
    }

}
