package com.example.di

import com.example.dao.UserDao
import com.example.redis.RedisClass
import com.example.redis.RedisClient
import com.example.redis.RedisSessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import com.example.repository.UserDaoImp
import com.example.service.UserService

val koinModule = module {
    singleOf(::UserDaoImp) { bind<UserDao>() }
    singleOf(::UserService)
    single<RedisClass>{RedisClass(RedisClient.jedis)}
    single<RedisSessionStorage>{RedisSessionStorage(RedisClient.jedis)}
}