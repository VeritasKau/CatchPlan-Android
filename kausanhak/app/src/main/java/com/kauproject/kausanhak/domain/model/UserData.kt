package com.kauproject.kausanhak.domain.model

data class UserData(
    var name: String = "",
    var token: String? = null,
    var num: String? = null,
    var gender: String = "",
    var mbti: String = "",
    var firstFavorite: String = "",
    var secondFavorite: String = "",
    var thirdFavorite: String = "",
)
