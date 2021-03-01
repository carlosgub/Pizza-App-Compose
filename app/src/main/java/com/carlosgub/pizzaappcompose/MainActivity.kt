package com.carlosgub.pizzaappcompose

import android.os.Bundle
import android.widget.Space
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
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.carlosgub.pizzaappcompose.data.DataDummy
import com.carlosgub.pizzaappcompose.model.Pizza
import com.carlosgub.pizzaappcompose.ui.theme.*

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
        modifier = modifier,
        tint = Color.White,
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
    val focusManager = LocalFocusManager.current
    TextField(
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_hint),
                style = TextStyle(color = Color.Black)
            )
        },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search_red),
                contentDescription = "search"
            )
        },
        modifier = modifier
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(MaterialTheme.colors.primary)
            .padding(top = 12.dp, bottom = 16.dp, start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .focusModifier(),
        colors = textFieldColors(backgroundColor = Color.White),
        value = query,
        onValueChange = {
            setQuery(it)
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hideSoftwareKeyboard()
                focusManager.clearFocus()
            },
        )
    )
}

@Composable
fun TopMenu() {
    Column {
        Row(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Menu",
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.primary
                )
            )
            OutlinedButton(
                onClick = {},
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.caption.copy(
                        color = Gray100
                    )
                )
            }
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
        backgroundColor = Coral100,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .size(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
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
                Text(
                    text = pizza.name,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = pizza.category,
                    style = MaterialTheme.typography.caption.copy(
                        color = Gray100
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                val price = stringResource(R.string.format_price, pizza.price)
                Text(
                    text = price,
                    style = MaterialTheme.typography.body2.copy(
                        fontWeight = FontWeight.Bold
                    ),
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
                .padding(start = 12.dp, end = 8.dp)
        ) {
            Text(
                text = "Hot Promo!",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )
        }
        PizzaHotPromo()
    }
}

@Composable
fun PizzaHotPromo() {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Red100,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .size(160.dp)
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
                    .size(height = 160.dp, width = 160.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Pizza Beef Cheese",
                    style = MaterialTheme.typography.body2.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(top = 6.dp)
                )
                Text(
                    text = "Pizza",
                    style = MaterialTheme.typography.caption.copy(
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    CreateIcon(
                        iconResource = painterResource(id = R.drawable.ic_clock),
                        modifier = Modifier
                            .size(12.dp)
                    )
                    Text(
                        text = "3 days left",
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 4.dp, end = 8.dp, bottom = 12.dp)
                ) {
                    val price2 = stringResource(R.string.format_price, 7.98)
                    Text(
                        text = price2,
                        style = MaterialTheme.typography.body2.copy(
                            color = GrayDiscount,
                            textDecoration = TextDecoration.LineThrough,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    val priceDiscount = stringResource(R.string.format_price, 5.98)
                    Text(
                        text = priceDiscount,
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                    )
                    Spacer(
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