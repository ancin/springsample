package com.springapp.mvc.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.xml.transform.sax.SAXTransformerFactory;

/**
 * jedis common
 *
 * @author songkejun
 * @create 2018-01-11 14:01
 **/
public class JedisCommon {

    private static  JedisPoolConfig config;
    private static  JedisPool pool;
    private static Jedis jedis;
    private static String HOST="127.0.0.1";
    private static int PORT =6379;
    private static int TIMEOUT =2000;


    public static JedisPoolConfig getJedisConfig(){
        if (config == null){
            config =  new JedisPoolConfig();
            config.setMaxIdle(8);
            config.setMaxTotal(18);
        }

        return config;
    }

    public static JedisPool getPool(){
        if (config == null){
            config = getJedisConfig();
        }
        if (pool ==null){
            pool = new JedisPool(config, HOST, PORT, TIMEOUT);
        }

        return pool;
    }

    public static void closeAll(){
        if (jedis!=null){
            jedis.close();
        }
        if (pool!=null){
            pool.close();
        }
    }
    public static void main(String[] args){

        JedisPool pool = getPool();
        Jedis jedis = pool.getResource();
        String user = jedis.get("user");

        System.out.println(">>>get redis user value:"+user);
        closeAll();

    }
}
