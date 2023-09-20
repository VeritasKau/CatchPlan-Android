package com.kauproject.kausanhak.domain.model

data class Event(
    val id: Int,
    val name: String,
    val place: String,
    val date: String,
    val image: String,
    val detailImage: String,
    val url: String
) {
    companion object{
        fun mock() = Event(
            id = 0,
            name = "[BORN PINK]",
            place = "고척 스카이돔",
            date = "2023.09.16 ~ 2023.09.17",
            image = "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif",
            detailImage = "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg",
            url = "https://tickets.interpark.com/goods/23011804"
        )
    }
}