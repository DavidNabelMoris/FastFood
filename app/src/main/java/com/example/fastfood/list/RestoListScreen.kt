    package com.example.fastfood.list

    import android.content.Context
    import android.content.Intent
    import android.util.Log
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Text
    import androidx.compose.material3.pulltorefresh.PullToRefreshBox
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import com.example.fastfood.model.FastFood
    import com.example.fastfood.request.FastFoodRequest
    import com.example.fastfood.storage.FastFoodStorage
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.tooling.preview.Preview
    import com.example.fastfood.RestoItemActivity
    import com.example.fastfood.storage.FastFoodStorage.estDans
    import com.example.fastfood.storage.FastFoodStorage.estDansIndice
    import com.example.fastfood.ui.theme.FastFoodTheme
    import coil.compose.rememberAsyncImagePainter
    import com.example.fastfood.utility.RestoLocation
    import com.example.fastfood.utility.RestoLocation.calculateDistance


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FastFoodScreen() {
        val context = LocalContext.current
        var fastFoods by remember { mutableStateOf(listOf<FastFood>()) }
        var allfastFoods by remember { mutableStateOf(listOf<FastFood>()) }
        var isRefreshing by remember { mutableStateOf(false) }


        // Chargement initial
        LaunchedEffect(Unit) {
            isRefreshing = true
            FastFoodRequest(context) { all->
                allfastFoods=all
                fastFoods = FastFoodStorage.get(context).findAll()
                isRefreshing = false
                Log.d("fast", "$allfastFoods")
            }
        }

        fun fusion_liste(api: List<FastFood>, locale:List<FastFood>,context: Context):List<FastFood>{
            val resList = mutableListOf<FastFood>()

            resList.addAll(locale)

            for (fastfood in api) {
                val existeDeja = estDans(context,fastfood.nom,fastfood.address)
                if (!existeDeja) {
                    resList.add(fastfood)
                }
            }
            return resList
        }
        val ListFastFood = remember(allfastFoods, fastFoods) {fusion_liste(allfastFoods,fastFoods,context)}

        // Affichage de la liste
        RestoListScreen(
            context = context,
            fastFoods = ListFastFood,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                FastFoodRequest(context) {all->
                    allfastFoods=all
                    fastFoods = FastFoodStorage.get(context).findAll()
                    isRefreshing = false
                }
            } ,
            FavorisChange = { List ->
                fastFoods = List
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RestoListScreen(
        modifier: Modifier = Modifier,
        context: Context = LocalContext.current,
        fastFoods: List<FastFood>,
        isRefreshing: Boolean,
        onRefresh: () -> Unit,
        FavorisChange:(List<FastFood>)-> Unit
    ) {
        var lat1 by remember { mutableStateOf<Double?>(null) }
        var lon1 by remember { mutableStateOf<Double?>(null) }

        LaunchedEffect(Unit) {
            RestoLocation.calcul_position(context) { latitude, longitude ->
                lat1 = latitude
                lon1 = longitude
                Log.d("DIST", "My location = $lat1,$lon1")
            }
        }
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(FastFoodTheme.colors.background)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ){
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(all = 26.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)


                    ) {
                        items(fastFoods) { food ->
                            var isFavoris = estDans(context, food.nom, food.address)
                            val heartIcon = if (isFavoris) com.example.fastfood.R.drawable.ic_favorite_filled
                            else com.example.fastfood.R.drawable.ic_favorite

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(FastFoodTheme.colors.surface)
                                    .clickable {
                                        Log.d("FastFoodClick", "Nom du resto : ${food.nom}")
                                        val intent = Intent(context, RestoItemActivity::class.java)

                                        val id_food_save: Int?=estDansIndice(context,food.nom,food.address)

                                        if(id_food_save!=null){
                                            val food_save=FastFoodStorage.get(context).find(id_food_save)
                                            intent.putExtra("fastfood", food_save)
                                            Log.d("FastFoodSAVE", "ON UTILISE LA SAUVEAGRDE")
                                        }
                                        else{
                                            intent.putExtra("fastfood", food) // un objet FastFood
                                        }
                                        context.startActivity(intent)

                                    }
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val imgName = java.net.URLEncoder.encode(food.nom, "UTF-8").replace("+", "%20")
                                val imageUrl = "http://51.68.91.213/gr-3-5/img/$imgName.png"

                                Image(
                                    painter = rememberAsyncImagePainter(model = imageUrl),
                                    contentDescription = "img de ${food.nom}",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape),

                                    contentScale = ContentScale.Crop
                                )
                                val distanceText =
                                    if (lat1 != null && lon1 != null) {
                                        val d = calculateDistance(lat1!!, lon1!!, food.latitude, food.longitude)
                                        String.format("%.1f km", d)
                                    } else {
                                        "Localisation en cours..."
                                    }


                                Text(
                                    text = "${food.nom}\n - ${food.address}\nDistance: $distanceText",
                                    color = FastFoodTheme.colors.textPrimary
                                )

                                IconButton(
                                    onClick = {
                                        isFavoris = !isFavoris
                                        val storage = FastFoodStorage.get(context)

                                        if (isFavoris) {
                                            // Ajouter dans le fichier local si pas déjà présent
                                            if (!estDans(context,food.nom,food.address)) {
                                                storage.insert(food.copy(favoris = true))
                                                Log.d("FastFoodUpdate", "Favori ajouté pour ${food.nom}")
                                            }
                                        }

                                        else{
                                            var id_supprime:Int?=estDansIndice(context,food.nom,food.address)

                                            if(id_supprime!=null){
                                                storage.delete(id_supprime)
                                                Log.d("FastFoodSupprimeFavoris", "Favori supprimer ${food.nom}")
                                            }
                                            else{
                                                Log.d("FastFoodSupprimeFavoris", "Favori introuvable pour ${food.nom}")
                                            }
                                        }
                                        // 🔄 Mettre à jour fastFoods pour déclencher recomposition
                                        FavorisChange(storage.findAll())
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(heartIcon),
                                        contentDescription = if (isFavoris) "Retirer des favoris" else "Ajouter aux favoris",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }


                        }
                    }
                }
            }
        }


    }
    @Preview(showBackground = true)
    @Composable
    fun RestoListScreenPreview() {
        FastFoodTheme {
            val sample = listOf(
                FastFood(
                    id = 1, nom = "Burger Street", address = "12 rue de la Paix",
                    note = 4.5f, latitude = 45.7640, longitude = 4.8357,
                    description = "Burgers & fries", favoris = true, horaires = emptyList()
                ),
                FastFood(
                    id = 2, nom = "Pizza Nova", address = "8 avenue des Alpes",
                    note = 4.1f, latitude = 45.1885, longitude = 5.7245,
                    description = "Wood-fired pizzas", favoris = false, horaires = emptyList()
                )
            )
            RestoListScreen(
                fastFoods = sample, isRefreshing = false, onRefresh = {}, FavorisChange = {}
            )
        }}

