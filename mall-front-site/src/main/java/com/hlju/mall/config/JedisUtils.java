package com.hlju.mall.config;

import com.deng.common.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static JedisPool jedisPool = null;

    private static volatile Jedis jedis = null;

    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    public JedisUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (JedisUtils.applicationContext == null) {
            //初始化 spring applicationContext
            JedisUtils.applicationContext = applicationContext;
        }
    }

    public static Jedis getJedis() {
        if (jedis == null) {
            synchronized (Jedis.class) {
                if (jedis == null) {
                    jedis = getJedisPool().getResource();
                }
            }
        }
        return jedis;
    }

    public static JedisPool getJedisPool() {
        if (jedisPool == null) {
            synchronized (JedisPool.class) {
                if (jedisPool == null) {
                    jedisPool = applicationContext.getBean("jedisPool", JedisPool.class);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 根据key查看是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        return getJedis().exists(key);
    }

    /**
     * 设置key -value 形式数据
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(String key, String value) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = "OK".equals(jedis.set(key, value));
        } catch (Exception e) {
            jedis.close();
            logger.error("JedisCache.set falid", e);
        }
        return result;
    }

    public static boolean set(String key, Object value) {
        boolean result = false;
        Jedis jedis = null;
        byte[] valueBytes = SerializeUtil.serialize(value);
        byte[] keyBytes = SerializeUtil.serialize(key);
        try {
            jedis = getJedis();
            result = "OK".equals(jedis.set(keyBytes, valueBytes));
        } catch (Exception e) {
            jedis.close();
            logger.error("JedisCache.set falid", e);
        }
        return result;
    }

    /**
     * 设置 一个过期时间
     *
     * @param key key
     * @param value value
     * @param timeOut 单位秒
     * @return
     */
    public static boolean set(String key, String value, int timeOut) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = "OK".equals(jedis.setex(key, timeOut, value));
        } catch (Exception e) {
            jedis.close();
            logger.error("JedisCache.set falid", e);
        }
        return result;
    }

    /**
     * 设置 一个过期时间
     *
     * @param key key
     * @param value value
     * @param timeOut 单位秒
     * @return
     */
    public static boolean set(String key, Object value, int timeOut) {
        boolean result = false;
        Jedis jedis = null;
        byte[] valueBytes = SerializeUtil.serialize(value);
        byte[] keyBytes = SerializeUtil.serialize(key);
        try {
            jedis = getJedis();
            result = "OK".equals(jedis.setex(keyBytes, timeOut, valueBytes));
        } catch (Exception e) {
            jedis.close();
            logger.error("JedisCache.set falid", e);
        }
        return result;
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    public static Object get(String key,boolean valueIsObj) {
        Object result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (valueIsObj){
                byte[] keyBytes = SerializeUtil.serialize(key);
                result = SerializeUtil.unserialize(jedis.get(keyBytes));
            }else{
                result = jedis.get(key);
            }
            jedis.close();
        } catch (Exception e) {
            jedis.close();
            logger.error("JedisCache.get falid", e);
        }
        return result;
    }

    /**
     * 根据key删除
     *
     * @param key
     */
    public static void del(String key,boolean valueIsObj) {
        if (valueIsObj){
            byte[] keyBytes = SerializeUtil.serialize(key);
            getJedis().del(keyBytes);
        }else {
            getJedis().del(key);
        }
    }
}
