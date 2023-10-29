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
}

val mockConcertEvents = listOf(
    Event(0, "블랙핑크", "올림픽홀", "2023-10-27 ~ 2023-10-31", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(1, "A", "올림픽홀", "2023-09-29", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(2, "B", "올림픽홀", "2023-09-21", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
)

val mockMusicalEvents = listOf(
    Event(3, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315"),
    Event(4, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315"),
    Event(5, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315")
)

val mockTheaterEvents = listOf(
    Event(6, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    Event(7, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    Event(8, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    )