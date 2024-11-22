package com.example.practica04.views

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.practica04.R
import com.example.practica04.model.Producto
import com.example.practica04.viewmodels.ProductoViewModel
import android.util.Base64
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.sp
import com.example.practica04.dialog.AlertaVacio
import com.example.practica04.ui.theme.bglightmaroon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoView(
    productId: Int,
    navController: NavController,
    viewModel: ProductoViewModel,
    modifier: Modifier = Modifier
) {
    val producto = viewModel.getProductById(productId)
    var name by remember { mutableStateOf(producto?.nombre ?: "") }
    var description by remember { mutableStateOf(producto?.descripcion ?: "") }
    val initialColor = producto?.color?.let { Color(android.graphics.Color.parseColor(it)) } ?: Color.White
    var selectedColor by remember { mutableStateOf(initialColor) }
    var backgroundImage by remember { mutableStateOf(producto?.imagen ?: "") }
    var useImageBackground by remember { mutableStateOf(backgroundImage.isNotBlank()) }

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
                        text = "Editar Nota",
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
            Spacer(modifier = Modifier.height(5.dp)) // Agrega espacio aquí
            // Título y descripción del producto
            campoTitulo(
                label = "Título",
                value = name,
                icono = R.color.pastel,
                onValueChange = { name = it },
                modifier = Modifier
                    .width(390.dp)
                    .height(80.dp)
            )

            textoNota(
                label = "Contenido",
                value = description,
                icono = R.color.pastel,
                onValueChange = { description = it },
                backgroundColor = selectedColor,
                modifier = Modifier
                    .width(390.dp)
                    .height(400.dp)
            )

            // Cambiar color de la nota
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
                            selectedColor = color
                        }
                    }
                }
            }



            // Botón para actualizar el producto
            Button(
                onClick = {
                    if (name.isBlank() || description.isBlank()) {
                        // Aquí puedes mostrar un mensaje de error
                    } else {
                        viewModel.updateProduct(
                            Producto(
                                id = producto?.id ?: 0,
                                nombre = name,
                                descripcion = description,
                                color = String.format("#%06X", 0xFFFFFF and selectedColor.toArgb()),
                                imagen = if (useImageBackground) backgroundImage else ""
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text("Actualizar Nota", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEditar(
    producto: Producto?,
    viewModel: ProductoViewModel,
    navController: NavController,
    selectedColor: Color,
    onColorChanged: (Color) -> Unit,
    backgroundImage: String,
    onImageSelected: (String) -> Unit,
    useImageBackground: Boolean,
    onBackgroundOptionChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf(producto?.nombre ?: "") }
    var description by remember { mutableStateOf(producto?.descripcion ?: "") }
    var color by remember { mutableStateOf(producto?.color ?: "#FFFFFF") }
    var selectedColorInternal by remember { mutableStateOf(selectedColor) }
    var errorMsg by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(selectedColor) {
        selectedColorInternal = selectedColor
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(9.dp)
            .background(selectedColorInternal.copy(alpha = 0.6f)),
        verticalArrangement = Arrangement.spacedBy(9.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        campoTitulo(label = "Título", value = name, icono = R.color.pastel, onValueChange = { name = it })
        textoNota(label = "Contenido", value = description, icono = R.color.pastel, onValueChange = { description = it })

        if (!useImageBackground) {
            Text(text = "Color de la nota", style = MaterialTheme.typography.bodyMedium)
            ColorPickers(selectedColor = selectedColorInternal) { newColor ->
                selectedColorInternal = newColor
                color = "#${newColor.toArgb().toString(16)}"
                onColorChanged(newColor)
            }
        } else {
            Text("Selecciona una imagen:")
            Button(
                onClick = { /* Logic for image picker */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cargar Imagen")
            }
        }

        Button(
            onClick = {
                if (name.isBlank() || description.isBlank()) {
                    errorMsg = "Favor poner un título y contenido"
                    showErrorDialog = true
                } else {
                    try {
                        val colorHex = String.format("#%06X", 0xFFFFFF and selectedColorInternal.toArgb())
                        val updatedImage = if (!useImageBackground) "" else backgroundImage

                        viewModel.updateProduct(
                            Producto(
                                id = producto?.id ?: 0,
                                nombre = name,
                                descripcion = description,
                                color = colorHex,
                                imagen = updatedImage
                            )
                        )
                        navController.popBackStack()
                    } catch (e: Exception) {
                        errorMsg = "Error al actualizar la nota"
                        showErrorDialog = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .padding(16.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "Actualizar Nota",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Actualizar Nota")
        }
    }

    if (showErrorDialog) {
        AlertaVacio(
            dialogTitle = "Error",
            dialogText = errorMsg,
            onDismissRequest = { showErrorDialog = false },
            onConfirmation = { showErrorDialog = false },
            show = showErrorDialog
        )
    }
}

