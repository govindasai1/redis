package com.example.redis

import com.example.exception.SomethingWentWrongException
import io.ktor.server.sessions.*
import redis.clients.jedis.Jedis
import redis.clients.jedis.exceptions.JedisConnectionException

class RedisSessionStorage(private val redisClient:Jedis):SessionStorage {
    private val REDIS_TTL: Long = 60 * 60 * 1 * 1
    private val tag = RedisSessionStorage::class.qualifiedName
    override suspend fun invalidate(id: String) {
        try {
            redisClient.del(id)
        } catch (connectionException: JedisConnectionException) {
            throw SomethingWentWrongException("UNABLE TO CONNECT")
        }
    }
    override suspend fun read(id: String): String {
        try {
            println("IN SESSION STORAGE $id")
            return redisClient.get(id) ?: throw NoSuchElementException("Session $id not found")
        } catch (connectionException: JedisConnectionException) {
            throw SomethingWentWrongException("UNABLE TO CONNECT")
        }
    }
    override suspend fun write(id: String, value: String) {
        try {
            redisClient[id] = value
            redisClient.expire(id, REDIS_TTL)
        } catch (connectionException: JedisConnectionException) {
            throw SomethingWentWrongException("UNABLE TO CONNECT")
        }
    }
}