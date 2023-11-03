package com.kauproject.kausanhak.domain.model

data class Event(
    val id: Int,
    val name: String,
    val place: String,
    val date: String,
    val image: String,
    val detailImage: String,
    val detailContent: String,
    val url: String
) {
}


val mockTheaterEvents = listOf(
    Event(6, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "예시","https://tickets.interpark.com/goods/23008639"),
    )