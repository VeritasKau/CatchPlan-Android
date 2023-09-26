package com.kauproject.kausanhak.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.kauproject.kausanhak.R

@Composable
fun Events(
    viewModel: MainViewModel,
    selectEvent: (Int) -> Unit
) {
    // mock data
    val selecetedTab = EventHomeTab.getTabFromResource(viewModel.selectedTab.value)
    val tabs = EventHomeTab.values()



}


enum class EventHomeTab(
    @StringRes val titile: Int,
    val icon: ImageVector
){
    HOME(R.string.menu_home, Icons.Filled.Home),
    CALENDAR(R.string.menu_calendar, Icons.Filled.DateRange),
    FAVORITE(R.string.menu_favorite, Icons.Filled.Star),
    MYPAGE(R.string.menu_my_page, Icons.Filled.Person);

    companion object{
        fun getTabFromResource(@StringRes resource: Int): EventHomeTab{
            return when(resource){
                R.string.menu_my_page -> MYPAGE
                R.string.menu_favorite -> FAVORITE
                R.string.menu_calendar -> CALENDAR
                else -> HOME
            }
        }
    }

}




