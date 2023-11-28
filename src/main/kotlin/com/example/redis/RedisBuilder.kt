package com.example.redis

import redis.clients.jedis.Jedis

class RedisBuilder(val host:String,val port:Int)
{
    fun connect():Jedis{
        val jedis=Jedis(host,port)
        jedis.connection.setTimeoutInfinite()
        return jedis
    }

}
