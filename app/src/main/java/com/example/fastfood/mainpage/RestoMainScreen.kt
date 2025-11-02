package com.example.fastfood.mainpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastfood.R
import com.example.fastfood.ui.theme.FastFoodTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.fastfood.list.FastFoodScreen

@Composable
fun RestoMainScreen(onUseMyLocation: () -> Unit) {
    val (query, setQuery) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FastFoodTheme.colors.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- Header: title + logo ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                painter = painterResource(id = R.drawable.logo), // drawable/fastfoods_logo.png
                // painter = painterResource(id = R.mipmap.ic_logo),       // if using launcher icon from mipmap
                contentDescription = "FastFoods logo",
                modifier = Modifier.size(80.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    color = FastFoodTheme.colors.textPrimary,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Use one of the two lines below depending on where your image is

            }

            Button(
                onClick = onUseMyLocation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = FastFoodTheme.colors.surface,
                    contentColor = FastFoodTheme.colors.textPrimary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = FastFoodTheme.colors.primary
                )
                Spacer(Modifier.width(10.dp))
                Text(text = stringResource(R.string.use_my_location))
            }

            Spacer(Modifier.height(16.dp))

            // --- Search field ---
            OutlinedTextField(
                value = query,
                onValueChange = setQuery,
                textStyle = TextStyle(color = FastFoodTheme.colors.textPrimary),
                placeholder = { Text(stringResource(R.string.search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
            )

            Spacer(Modifier.height(16.dp))

            // --- Category chips ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryChip("Burgers"); CategoryChip("Pizza"); CategoryChip("Asian")
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CategoryChip("Vegan"); CategoryChip("Desserts"); CategoryChip("Kebab")
            }

            FastFoodScreen()
        }

        // --- Floating action button ---
        FloatingActionButton(
            onClick = { /* TODO */ },
            containerColor = FastFoodTheme.colors.primary,
            contentColor = FastFoodTheme.colors.onPrimary,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
        }
    }
}


@Composable
private fun CategoryChip(label: String) {
    Box(
        modifier = Modifier
            .height(38.dp)
            .defaultMinSize(minWidth = 90.dp)
            .background(
                color = FastFoodTheme.colors.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = FastFoodTheme.colors.textPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RestoMainScreenPreview() {
    FastFoodTheme { RestoMainScreen({}) }
}
