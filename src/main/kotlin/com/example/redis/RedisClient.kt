package com.example.redis

import com.google.gson.Gson
import redis.clients.jedis.Jedis

object RedisClient {

    val jedis: Jedis = RedisBuilder("localhost", 6379).connect()

}
class RedisClass(private val jedis:Jedis){

    fun set(key: String, value: Any) {
        val jsonString = Gson().toJson(value)
        jedis.set(key, jsonString.trim())
    }
    fun getString(key: String): String? {
        return Gson().toJson(jedis.get(key))
    }
    fun delete(key:String){
        jedis.del(key)
    }
}