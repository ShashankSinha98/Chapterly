package com.lucifer.chapterly.book.presentation.book_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chapterly.composeapp.generated.resources.Res
import chapterly.composeapp.generated.resources.book_cover
import chapterly.composeapp.generated.resources.book_error_2
import chapterly.composeapp.generated.resources.go_back
import chapterly.composeapp.generated.resources.mark_as_favorite
import chapterly.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import com.lucifer.chapterly.core.presentation.DarkBlue
import com.lucifer.chapterly.core.presentation.DesertWhite
import com.lucifer.chapterly.core.presentation.SandYellow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null) // State to hold the result of the image loading
    }

    val painter = rememberAsyncImagePainter(
        model = imageUrl, // image URL
        onSuccess = {
            // Check if image has dimensions
            imageLoadResult = if(it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Image has no dimensions"))
            }
        }, onError = {
            it.result.throwable.printStackTrace() // Log the error
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Blurred Top Cover Image
            Box(
                modifier = Modifier
                    .weight(0.3f) // 30% of the height
                    .fillMaxWidth()
                    .background(DarkBlue)
            ) {
                imageLoadResult?.getOrNull()?.let { painter ->
                    Image(
                        painter = painter,
                        contentDescription = stringResource(Res.string.book_cover),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp) // Apply blur effect
                    )
                }
            }

            // White Bottom Section
            Box(
                modifier = Modifier
                    .weight(0.7f) // 70% of the height
                    .fillMaxWidth()
                    .background(DesertWhite)
            )

        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            // Spacer to push content below the top icons
            Spacer(modifier = Modifier.fillMaxHeight(0.15f))

            // Book Cover Card
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp)
                    .aspectRatio(2/3f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color.Transparent
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 15.dp
                )
            ) {
                AnimatedContent(
                    targetState = imageLoadResult
                ) { result ->
                    when(result) {
                        null -> CircularProgressIndicator()
                        else -> {
                            Box{
                                Image(
                                    painter = if(result.isSuccess) painter else {
                                        painterResource(Res.drawable.book_error_2)
                                    },
                                    contentDescription = stringResource(Res.string.book_cover),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent),
                                    contentScale = if(result.isSuccess) {
                                        ContentScale.Crop
                                    } else {
                                        ContentScale.Fit
                                    }
                                )

                                IconButton(
                                    onClick = onFavoriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    SandYellow, Color.Transparent
                                                ),
                                                radius = 70f
                                            )
                                        )
                                ) {
                                    Icon(
                                        imageVector = if(isFavorite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Outlined.FavoriteBorder
                                        },
                                        tint = Color.Red,
                                        contentDescription = if(isFavorite) {
                                            stringResource(Res.string.remove_from_favorites)
                                        } else {
                                            stringResource(Res.string.mark_as_favorite)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            content()
        }
    }
}

@Preview
@Composable
fun BlurredImageBackgroundPreview() {
    BlurredImageBackground(
        imageUrl = "https://example.com/image.jpg",
        isFavorite = false,
        onFavoriteClick = {},
        onBackClick = {}
    ) {
        // Preview content
    }
}