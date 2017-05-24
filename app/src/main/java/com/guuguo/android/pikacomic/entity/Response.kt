package com.guuguo.android.pikacomic.entity

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
data class TokenResponse(var token: String)
data class CategoryResponse(var categories: List<CategoryEntity>? = arrayListOf())
data class ComicsRandomResponse(var comics: List<ComicsEntity>? = arrayListOf())
