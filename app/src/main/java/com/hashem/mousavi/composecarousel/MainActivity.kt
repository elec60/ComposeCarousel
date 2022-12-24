package com.hashem.mousavi.composecarousel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hashem.mousavi.composecarousel.ui.Carousel
import com.hashem.mousavi.composecarousel.ui.theme.ComposeCarouselTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val items = mutableListOf<MyData>().apply {
            repeat(30) {
                this.add(MyData("Title ${it + 1}"))
            }
        }

        setContent {
            ComposeCarouselTheme {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "itemsPerPage = 3", modifier = Modifier.padding(horizontal = 16.dp))
                    Carousel(
                        list = items,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .height(150.dp),
                        itemsPerPage = 3,
                        minScale = 0.4f
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "itemsPerPage = 5", modifier = Modifier.padding(horizontal = 16.dp))
                    Carousel(
                        list = items,
                        modifier = Modifier
                            .background(color = Color.LightGray)
                            .fillMaxWidth()
                            .height(150.dp),
                        itemsPerPage = 5,
                        minScale = 0.4f
                    )
                }



            }
        }
    }
}
