package com.carlosgub.pizzaappcompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlosgub.pizzaappcompose.data.DataDummy
import com.carlosgub.pizzaappcompose.model.Pizza
import com.carlosgub.pizzaappcompose.ui.theme.PizzaAppComposeTheme
import com.carlosgub.pizzaappcompose.ui.theme.coral100
import com.carlosgub.pizzaappcompose.ui.theme.red100

@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@ExperimentalComposeUiApi
@Composable
fun AppPreview() {
    App()
}

@ExperimentalComposeUiApi
@Composable
fun App() {
    PizzaAppComposeTheme {
        Scaffold(
            topBar = { AppBar() }
        ) {
            BodyApp()
        }
    }
}

@Composable
fun AppBar() {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val pizzaIcon = painterResource(id = R.drawable.ic_pizza_icon)
                CreateIcon(
                    iconResource = pizzaIcon,
                    modifier = Modifier
                        .size(32.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        },
        actions = {
            val heartIcon = painterResource(id = R.drawable.ic_white_heart)
            CreateIcon(
                iconResource = heartIcon,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
private fun CreateIcon(iconResource: Painter, modifier: Modifier = Modifier) {
    Icon(
        painter = iconResource,
        contentDescription = null,
        modifier = modifier
    )
}

@ExperimentalComposeUiApi
@Composable
fun BodyApp() {
    Column {
        Search()
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState
        ) {
            item { TopMenu() }
            item { HotPromo() }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Search(modifier: Modifier = Modifier) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        label = {
            Text(
                text = stringResource(id = R.string.search_hint),
                style = TextStyle(color = Color(0xFFC7C7C7))
            )
        },
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(MaterialTheme.colors.primary)
            .padding(top = 12.dp, bottom = 16.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Transparent),
        value = query,
        onValueChange = {
            setQuery(it)
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hideSoftwareKeyboard()
        })
    )
}

@Composable
fun TopMenu() {
    Column {
        Row(
            modifier = Modifier
                .padding(top = 12.dp, start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = "Top Menu",
                modifier = Modifier
                    .weight(1f)
            )
            Text(text = "See all")
        }
        LazyRow {
            items(DataDummy.getTopMenuPizza().toList()) {
                PizzaTopMenuRow(
                    pizza = it
                )
            }
        }
    }
}

@Composable
fun PizzaTopMenuRow(pizza: Pizza) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = coral100,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 6.dp)
            .size(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            val pizzaImage = painterResource(id = pizza.image)
            Image(
                painter = pizzaImage,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(12.dp)
                    .size(height = 80.dp, width = 200.dp)
                    .fillMaxWidth()
            )
            Text(text = pizza.name)
            Text(text = pizza.category)
            Row(verticalAlignment = Alignment.CenterVertically) {
                val price = stringResource(R.string.format_price, pizza.price)
                Text(
                    text = price,
                    modifier = Modifier
                        .weight(1f)
                )
                val button = painterResource(id = R.drawable.ic_button_plus)
                Image(
                    painter = button,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun HotPromo() {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Text(
                text = "Hot Promo!"
            )
        }
        PizzaHotPromo()
    }
}

@Composable
fun PizzaHotPromo() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = red100,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 6.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            val pizzaImage = painterResource(id = R.drawable.pizza_2)
            Image(
                painter = pizzaImage,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(12.dp)
                    .size(height = 160.dp, width = 160.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
                    .weight(1f)
            ) {
                Text(
                    text = "Pizza Beef Cheese"
                )
                Text(
                    text = "Pizza"
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val price = stringResource(R.string.format_price, 5.98)
                    Text(
                        text = price,
                        modifier = Modifier
                            .weight(1f)
                    )
                    val button = painterResource(id = R.drawable.ic_button_plus_white)
                    Image(
                        painter = button,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            }
        }
    }
}