package com.it.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangzy on 2018/4/23.
 */
public class CacheManager<T> {

    private Map<String, T> cache = new ConcurrentHashMap<String, T>();

    public T selectValue(Object key) {
        return cache.get(key);
    }

    public void insertValue(String key, T value) {
        cache.put(key, value);
    }

    public void updateValue(String key, T value) {
        if (cache.keySet().contains(key)){
            cache.put(key, value);
        }
    }

    public void insertOrUpdateValue(String key, T value) {
        cache.put(key, value);
    }

    public void delete(String key){
        if (cache.keySet().contains(key)){
            cache.remove(key);
        }
    }

    public void delete(){
        cache.clear();
    }

}
