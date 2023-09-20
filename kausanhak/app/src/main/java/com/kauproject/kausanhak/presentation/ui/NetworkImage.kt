package com.kauproject.kausanhak.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.kauproject.kausanhak.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PaletteLoadedListener
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    circularRevealEnabled: Boolean = false,
    contentScale: ContentScale = ContentScale.Crop,
    paletteLoadedListener: PaletteLoadedListener? = null
){
    CoilImage(
        imageModel = { url },
        modifier = modifier,
        imageOptions = ImageOptions(contentScale = contentScale),
        component = rememberImageComponent {
            if(circularRevealEnabled){
                +CircularRevealPlugin()
            }else{
                +CrossfadePlugin(duration = 150)
            }
            if(paletteLoadedListener != null){
                +PalettePlugin(paletteLoadedListener = paletteLoadedListener)
            }
        },
        previewPlaceholder = R.drawable.sample,
        failure = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "image request failed"
                )
            }
        },
    )
}

@Preview
@Composable
private fun NetworkImagePreview(){
    NetworkImage(
        url = "",
        modifier = Modifier.fillMaxSize()
    )
}