package com.arioatlas.eshop.cahce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;

public class RedisCache implements Cache {

    private ObjectMapper objectMapper;
    private Jedis jedis;

    public RedisCache(ObjectMapper objectMapper,Jedis jedis){
        this.jedis = jedis;
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    private Collection<Object> getRedisList(String key, Class type){
        Collection<Object> list = new ArrayList<>();
        jedis.smembers(key).forEach(item->{
            try {
                list.add(objectMapper.readValue(item,type));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return list;
    }

    @Override
    public Object getItem(String key, Class type) {
        String json = jedis.get(key);
        try {
            return objectMapper.readValue(json , type);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object setItem(String key, Object item) {
        try {
            String json = objectMapper.writeValueAsString(item);
            String cached = jedis.set(key,json);
            return objectMapper.readValue(cached,Object.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public void removeItem(String key) {
        jedis.del(key);
    }

    @Override
    public Collection<Object> getList(String key, Class type) {
        return getRedisList(key,type);
    }

    @Override
    public Collection<Object> addItemToList(String key, Object item) {
        try {
            String json = objectMapper.writeValueAsString(item);
            jedis.sadd(key,json);

            return getRedisList(key,item.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Object> removeItemFromList(String key, Object item) {
        getRedisList(key,item.getClass()).forEach(cached->{
            if(cached.equals(item)){
                try {
                    String json = objectMapper.writeValueAsString(cached);
                    jedis.srem(key,json);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("not found");
            }
        });

        return getRedisList(key,item.getClass());
    }
}
