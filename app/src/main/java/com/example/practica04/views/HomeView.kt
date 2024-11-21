package com.example.practica04.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.practica04.R
import com.example.practica04.navigation.ListaProductos
import com.example.practica04.ui.theme.Pink40
import com.example.practica04.ui.theme.Pink80
import com.example.practica04.ui.theme.Purple80
import com.example.practica04.ui.theme.PurpleGrey40
import com.example.practica04.ui.theme.PurpleGrey80
import com.example.practica04.ui.theme.backg
import com.example.practica04.ui.theme.bglightmaroon
import com.example.practica04.ui.theme.buttonp
import com.example.practica04.ui.theme.containerhome
import com.example.practica04.ui.theme.contentBorder
import com.example.practica04.ui.theme.lineBorder
import com.example.practica04.ui.theme.maroon
import com.example.practica04.ui.theme.notebg
import com.example.practica04.ui.theme.textp


@Composable
fun HomeView(navController: NavController, modifier: Modifier = Modifier) {
    val backgroundColor = MaterialTheme.colorScheme.primaryContainer
    val buttonColor = MaterialTheme.colorScheme.primary

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .fillMaxSize()
            .background(backg)
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Card que envuelve los elementos
            Card(
                modifier = Modifier
                    .width(500.dp)
                    .height(295.dp)
                    .padding(18.dp)
                    .border(5.dp, lineBorder, RoundedCornerShape(12.dp))  // Borde con esquinas redondeadas
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = contentBorder
                )
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.unison),
                        contentDescription = "Logo",
                        modifier = Modifier.padding(top = 20.dp)
                    )

                }

            }

            Card(
                modifier = Modifier
                    .width(280.dp)
                    .height(90.dp),
                colors = CardDefaults.cardColors(
                    containerColor = containerhome // Cambia esto por el color deseado
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xC3F06997), // Naranja cálido
                                    Color(0xFFC887E0)
                                ) // Gradiente de morados pastel
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start, // Alineación horizontal hacia el inicio
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp) // Espaciado lateral
                    ) {
                        // Ícono a la izquierda
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Agregar Nueva Nota",
                            modifier = Modifier
                                .size(40.dp) // Tamaño del ícono
                                .padding(end = 8.dp), // Espaciado entre ícono y texto
                            tint = bglightmaroon
                        )

                        // Texto al lado derecho del ícono
                        Text(
                            text = "DoodleNote",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 35.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                                lineHeight = 48.sp
                            ),
                            color = bglightmaroon
                        )
                    }
                }

            }
        }


            Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
                Button(
                    onClick = { navController.navigate(ListaProductos) },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .padding(horizontal = 20.dp)
                        .height(45.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFFF8D2DC), // Rosa pastel claro
                                    Color(0xFFF8A1D1), // Rosa pastel vibrante
                                    Color(0xFFF1A3D6)
                                )  // Colores del degradado
                            ),
                            shape = CircleShape // Forma rectangular sin bordes redondeados
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,  // Hacemos que el color del fondo sea transparente ya que usamos un degradado
                        contentColor = Color.White
                    ),
                    shape = CircleShape // Forma rectangular sin bordes redondeados
                )
                {
                Text(
                    text = "Notas",
                    color = bglightmaroon,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 25.sp, // Tamaño más pequeño para el texto
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Andrea Estefania Arizaga Hernández",
            style = MaterialTheme.typography.titleLarge,
            color = buttonp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)

        )
    }
}

@Preview
@Composable
fun homevista(navController: NavController = rememberNavController()) {
    HomeView(navController)
}
