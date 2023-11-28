package com.kauproject.kausanhak.data.remote.repository

import com.kauproject.kausanhak.data.remote.service.promotion.GetPromotionService
import com.kauproject.kausanhak.domain.State
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PromotionRepositoryImpl(
    private val getPromotionService: GetPromotionService
): PromotionRepository {
    private var promotionList = emptyList<Event>()
    override fun fetchPromotion(): Flow<State<List<Event>>> = flow {
        emit(State.Loading)

        val response = getPromotionService.getPromotion()
        val statusCode = response.code()

        if(statusCode == 200){
            promotionList = response.body()?.map {
                Event(
                    id = (it.id?.times(1000)) ?: 0,
                    name = it.text ?: "",
                    place = it.place ?: "",
                    date = it.duration ?: "",
                    image = it.image ?: "",
                    detailImage = it.detail ?: "",
                    detailContent = it.detail2 ?: "",
                    url = it.url ?: ""
                )
            } ?: emptyList()

            emit(State.Success(promotionList))
        }else{
            emit(State.ServerError(statusCode))
        }
    }.catch { e->
        emit(State.Error(e))
    }

    override fun findPromotion(id: Int): Event = promotionList.find { it.id == id }!!
    override fun getPromotion(): List<Event> = promotionList
}