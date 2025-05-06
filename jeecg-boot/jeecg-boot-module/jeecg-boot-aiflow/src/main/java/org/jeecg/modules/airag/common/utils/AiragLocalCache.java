//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class AiragLocalCache {
    private static final Map<String, Cache<String, Object>> CACHE_MAP = new ConcurrentHashMap();

    public AiragLocalCache() {
    }

    public static <T> T get(String cacheType, String key) {
        Cache var2 = getCache(cacheType);
        return (T) var2.getIfPresent(key);
    }

    public static void put(String cacheType, String key, Object value) {
        Cache var3 = getCache(cacheType);
        var3.put(key, value);
    }

    public static void remove(String cacheType, String key) {
        if (CACHE_MAP.containsKey(cacheType)) {
            Cache var2 = (Cache)CACHE_MAP.get(cacheType);
            var2.invalidate(key);
        }

    }

    public static void clear(String cacheType) {
        if (CACHE_MAP.containsKey(cacheType)) {
            Cache var1 = (Cache)CACHE_MAP.get(cacheType);
            var1.invalidateAll();
        }

    }

    private static Cache<String, Object> getCache(String cacheType) {
        if (!CACHE_MAP.containsKey(cacheType)) {
            Cache var1 = CacheBuilder.newBuilder().maximumSize(300L).expireAfterWrite(5L, TimeUnit.MINUTES).build();
            CACHE_MAP.put(cacheType, var1);
        }

        return (Cache)CACHE_MAP.get(cacheType);
    }
}
