

    package com.example.fastfood.composable

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color

    @Composable
    fun RestoListScreen(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            //Text(text = "test")
            Column (
                modifier = Modifier.fillMaxWidth()
            ){
                    LazyColumn {
                    // Add a single item
                    item {
                        Text(text = "First item")
                    }
                    // Add 5 items
                    items(5) { index ->
                        Text(text = "Item: $index")

                    }

                    // Add another single item
                    item {
                        Text(text = "Last item")
                    }
                }
            }
        }
    }



