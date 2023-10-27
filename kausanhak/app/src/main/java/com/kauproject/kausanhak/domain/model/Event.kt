package com.kauproject.kausanhak.domain.model

data class Event(
    val id: Long,
    val name: String,
    val place: String,
    val date: String,
    val image: String,
    val detailImage: String,
    val url: String
) {
    companion object{
        fun mock() = Event(
            id = 0L,
            name = "[BORN PINK]",
            place = "고척 스카이돔",
            date = "2023.09.16 ~ 2023.09.17",
            image = "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif",
            detailImage = "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg",
            url = "https://tickets.interpark.com/goods/23011804"
        )
    }
}

val mockConcertEvents = listOf(
    Event(0L, "블랙핑크", "올림픽홀", "2023-10-27 ~ 2023-10-31", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(1L, "A", "올림픽홀", "2023-09-29", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
    Event(2L, "B", "올림픽홀", "2023-09-21", "https://ticketimage.interpark.com/Play/image/large/23/23011804_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23011804-08.jpg", "https://tickets.interpark.com/goods/23011804"),
)

val mockMusicalEvents = listOf(
    Event(3L, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315"),
    Event(4L, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315"),
    Event(5L, "순신", "예술의전당", "2023-10-12", "https://ticketimage.interpark.com/Play/image/large/23/23012315_p.gif", "https://ticketimage.interpark.com/230123152023/09/20/6cb6f097.jpg", "https://tickets.interpark.com/goods/23012315")
)

val mockTheaterEvents = listOf(
    Event(6L, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    Event(7L, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    Event(8L, "연극<수상한흥신소>", "소극장", "2023-10-23", "https://ticketimage.interpark.com/Play/image/large/23/23008639_p.gif", "https://ticketimage.interpark.com/Play/image/etc/23/23008639-02.jpg", "https://tickets.interpark.com/goods/23008639"),
    )