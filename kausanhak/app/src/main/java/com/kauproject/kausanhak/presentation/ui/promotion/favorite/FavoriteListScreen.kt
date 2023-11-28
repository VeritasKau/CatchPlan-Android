import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kauproject.kausanhak.R
import com.kauproject.kausanhak.domain.model.Event
import com.kauproject.kausanhak.presentation.ui.event.EventCard
import com.kauproject.kausanhak.presentation.ui.promotion.favorite.FavoriteViewModel
import com.kauproject.kausanhak.presentation.ui.promotion.place.PlaceViewModel

private val CardWidth = 170.dp
private val CardPadding = 16.dp

@Composable
fun FavoriteListScreen(
    onEventClick: (Int) -> Unit,
    navController: NavController
) {
    val viewModel: FavoriteViewModel = hiltViewModel()
    val event = viewModel.favoriteList.collectAsState()
    val scroll = rememberScrollState(0)
    val gradientWidth = with(LocalDensity.current){
        (6 * (CardWidth + CardPadding).toPx())
    }

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
        ,
        topBar = { CommonListTopBar(
            title = stringResource(id = R.string.promotion_favorite_title),
            backPress = { navController.navigateUp() }
        ) },
        containerColor = Color.White
    ) {paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
            ,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ){
            itemsIndexed(event.value){ index, item ->
                EventCard(
                    modifier = Modifier
                        .padding(15.dp)
                    ,
                    event = item,
                    index = index,
                    onEventClick = onEventClick,
                    gradientWidth = gradientWidth,
                    scroll = scroll.value
                )
            }
        }

    }
}

@Composable
private fun CommonListTopBar(
    title: String,
    backPress: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .height(50.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                ),
            onClick = backPress
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.detail_back),
                tint = Color.Black
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 36.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                ,
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

    }

}
