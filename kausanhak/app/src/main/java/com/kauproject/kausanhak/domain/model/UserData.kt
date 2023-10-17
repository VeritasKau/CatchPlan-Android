package com.kauproject.kausanhak.domain.model

data class UserData(
    val name: String = "",
    val gender: String = "",
    val mbti: String = "",
    var firstFavorite: String? = null,
    var secondFavorite: String? = null,
    var thirdFavorite: String? = null,
)
