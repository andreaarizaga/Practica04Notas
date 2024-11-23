package com.example.practica04.views

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practica04.R

import com.example.practica04.model.Producto
import com.example.practica04.viewmodels.ProductoViewModel
import java.io.InputStream
import androidx.compose.ui.graphics.painter.BitmapPainter
import android.graphics.Bitmap
import android.graphics.Paint.Align
import android.util.Base64
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asAndroidBitmap
import com.example.practica04.ui.theme.PurpleGrey40
import com.example.practica04.ui.theme.backg
import com.example.practica04.ui.theme.bglightmaroon
import com.example.practica04.ui.theme.colortxt
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductosView(navController: NavController, viewModel: ProductoViewModel, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.White) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xC3F06997), // Naranja cálido
                                Color(0xFFC887E0)  // Amarillo suave
                            )
                        )
                    )
            )
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        text = "Nota",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 30.sp,
                            letterSpacing = 1.5.sp
                        ),
                        color = bglightmaroon
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Regresar",
                                    modifier = Modifier
                                    .size(30.dp), // Tamaño del ícono
                            tint = bglightmaroon
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Formulario(
                viewModel = viewModel,
                navController = navController,
                selectedColor = selectedColor,
                onColorSelected = { selectedColor = it }
            )
        }
    }
}

@Composable
fun campoTitulo(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textArea: Boolean = false,
    icono: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .width(400.dp)
            .height(if (textArea) 200.dp else 80.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = backg
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        ),
        shape = RoundedCornerShape(50.dp)
    )
}

@Composable
fun textoNota(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textArea: Boolean = false,
    icono: Int = 0,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent // Aquí se pasa el color de fondo
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .width(400.dp)
            .height(if (textArea) 200.dp else 400.dp), // Ajusta el alto dependiendo de si es un área de texto
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = backgroundColor, // Color de fondo cuando no está enfocado
            focusedContainerColor = backgroundColor // Color de fondo cuando está enfocado
        ),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp
        ),
        shape = RoundedCornerShape(15.dp)
    )
}


@Composable
fun ColorPickers(selectedColor: Color, onColorSelected: (Color) -> Unit) {
    val Red = Color(0x6FB894EB)
    val Green = Color(0x85FFF8B8)
    val Blue = Color(0xFFE5FFE0)
    val Yellow = Color(0x8BDEFFFC)
    val Gray = Color(0xFFBED7FD)

    val colors = listOf(Red, Green, Blue, Yellow, Gray)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, shape = RoundedCornerShape(12.dp))
                    .border(
                        width = if (color == selectedColor) 4.dp else 0.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        onColorSelected(color)
                    }
            )
        }
    }
}

@Composable
fun Formulario(
    viewModel: ProductoViewModel,
    navController: NavController,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Fondo de imagen o color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            campoTitulo(
                label = "Título",
                value = name,
                icono = R.color.pastel,
                onValueChange = { name = it }

            )

            textoNota(
                label = "Contenido",
                value = description,
                icono = R.color.pastel,
                onValueChange = { description = it },
                backgroundColor = selectedColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .width(250.dp)
                    .height(90.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0x5CCF99CF))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Defina el color de nota:")
                        ColorPickers(selectedColor = selectedColor) { color ->
                            onColorSelected(color)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(85.dp)
                    .padding(16.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xC3F06997), // Naranja cálido
                                Color(0xFFC887E0)  // Amarillo suave
                            )
                        ),
                        shape = RoundedCornerShape(12.dp) // Ajusta los bordes redondeados
                    )
                    .clickable {
                        if (name.isBlank() && description.isBlank()) {
                            // Mostramos un mensaje de error si no hay título ni contenido
                        } else {
                            viewModel.addProduct(
                                Producto(
                                    nombre = name,
                                    descripcion = description,
                                    color = String.format("#%06X", 0xFFFFFF and selectedColor.toArgb())
                                )
                            )
                            navController.popBackStack()
                        }
                    }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        "Agregar Nota",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

        }
    }
}



