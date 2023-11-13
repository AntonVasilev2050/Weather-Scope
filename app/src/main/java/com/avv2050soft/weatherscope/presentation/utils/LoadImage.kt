package com.avv2050soft.weatherscope.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.avv2050soft.weatherscope.R

@Composable
fun LoadImage(
    data: Any?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_image_24),
        alpha = 0.95f,
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier
    )
}