package com.sp2p.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sp2p.constants.IConstants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClientPool {
	
	
	
	
	public static RedisClientPool redisClientPool = getInstance();  
    
    public static JedisPool jedisPool;  
      
    public static synchronized RedisClientPool getInstance()  
    {  
        if (null == redisClientPool)  
        {  
            redisClientPool = new RedisClientPool();  
        }  
        return redisClientPool;  
    }  
      
    public RedisClientPool(){  
        if (null == jedisPool)  
        {  
            init();  
        } 
	
    }
    
    
    
    private static JedisPoolConfig initPoolConfig()  
    {  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        // 控制一个pool最多有多少个状态为idle的jedis实例  
        jedisPoolConfig.setMaxIdle(1000);  
        // 最大能够保持空闲状态的对象数  
        jedisPoolConfig.setMaxIdle(300);  
        // 超时时间  
        jedisPoolConfig.setMaxWait(1000);  
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；  
        jedisPoolConfig.setTestOnBorrow(true);  
        // 在还会给pool时，是否提前进行validate操作  
        jedisPoolConfig.setTestOnReturn(true);  
          
        return jedisPoolConfig;  
    }  
      
    /** 
     * 初始化jedis连接池 
     */  
    public static void init()  
    {  
        JedisPoolConfig jedisPoolConfig = initPoolConfig();  
        String host =   IConstants.REDIS_IPADDR;
        		 
        int port =  Integer.parseInt(IConstants.REDIS_PORT);//6379;  
        int timeout = Integer.parseInt(IConstants.REDIS_TIMEOUT);//60000;  
        // 构造连接池  
        jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);  
    }  
	
	 

	public static void main(String[] args) {
		
		 Jedis redisClient = null;  
	        try{  
	            redisClient = RedisClientPool.jedisPool.getResource();  
	            
	            redisClient.hset("gjc", "g", "1010_g11");
	            redisClient.hset("gjc", "j", "1010_j");
	            redisClient.hset("gjc", "c", "1010_c");
	            
	            List<String> list = redisClient.hmget("gjc", "g","j","c");
	            for(String v : list){
	            	System.out.println("value:........................."+v);
	            }
	            
	            redisClient.append("desc", "123");
	            System.out.println(redisClient.get("desc"));
	            
	            
	            
	            Map<String,String> map = new HashMap<String,String>();
	            map.put("hello", "你好你好");
	            map.put("wang", "王五王五王五王五");
	            map.put("zhao", "赵四你好你好你好");
	            map.put("ok", "redis已经成功");
	            
	            redisClient.hmset("map", map);
	            
	            
	             map = redisClient.hgetAll("map");
	             System.out.println(map.size());
	            Set<String> set = map.keySet();
	            Iterator<String> iterator = set.iterator();
	            while(iterator.hasNext()){
	            	String key = iterator.next();
	            	String val = map.get(key); 
	            	
	            	System.out.println(key+":"+val+"//////");
	            }
	            
	        }   
	        catch (Exception e){ 
	        	System.out.println("出现异常.................");
	            RedisClientPool.jedisPool.returnBrokenResource(redisClient);  
	        }finally{  
	            RedisClientPool.jedisPool.returnResource(redisClient);  
	        }  

	}

}
