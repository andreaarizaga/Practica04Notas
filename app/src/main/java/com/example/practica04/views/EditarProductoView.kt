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
import androidx.compose.material3.Switch
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import com.example.practica04.dialog.AlertaVacio


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoView(
    productId: Int,
    navController: NavController,
    viewModel: ProductoViewModel,
    modifier: Modifier = Modifier
) {
    val producto = viewModel.getProductById(productId)
    val initialColor = producto?.color?.let { Color(android.graphics.Color.parseColor(it)) } ?: Color.White
    var backgroundColor by remember { mutableStateOf(initialColor) }
    var backgroundImage by remember { mutableStateOf(producto?.imagen ?: "") }
    var useImageBackground by remember { mutableStateOf(backgroundImage.isNotBlank()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(
                        text = "Editar",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()

        ) {

            if (useImageBackground && backgroundImage.isNotBlank()) {
                val imageBytes = Base64.decode(backgroundImage, Base64.DEFAULT)
                val imageBitmap = imageBytes.inputStream().use { BitmapFactory.decodeStream(it) }.asImageBitmap()

                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Imagen del fondo",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .alpha(0.5f),
                    contentScale = ContentScale.Crop
                )
            } else {

                Box(modifier = Modifier.fillMaxSize().background(backgroundColor))
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                FormularioEditar(
                    producto = producto,
                    viewModel = viewModel,
                    navController = navController,
                    selectedColor = backgroundColor,
                    onColorChanged = { newColor ->
                        backgroundColor = newColor
                    },
                    backgroundImage = backgroundImage,
                    onImageSelected = { newImage ->
                        backgroundImage = newImage
                    },
                    useImageBackground = useImageBackground,
                    onBackgroundOptionChanged = { useImage ->
                        useImageBackground = useImage
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (!useImageBackground) {

                }
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
    var showImagePickerDialog by remember { mutableStateOf(false) }

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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Usar fondo de imagen", style = MaterialTheme.typography.bodyMedium)
            Switch(
                checked = useImageBackground,
                onCheckedChange = { onBackgroundOptionChanged(it) }
            )
        }

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
                onClick = { showImagePickerDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cargar Imagen")
            }
        }

        Button(
            onClick = {
                if (producto != null) {
                    if (name.isBlank() && description.isBlank()) {
                        errorMsg = "Favor poner un titulo y contenido"
                        showErrorDialog = true
                    }
                    else if(name.isBlank()){
                        errorMsg = "Favor poner un título"
                        showErrorDialog = true
                    }
                    else if(description.isBlank()){
                        errorMsg = "Favor poner un contenido"
                        showErrorDialog = true
                    }
                    else{
                        try {
                            val colorHex = String.format("#%06X", 0xFFFFFF and selectedColorInternal.toArgb())


                            val updatedImage = if (!useImageBackground) "" else backgroundImage


                            viewModel.updateProduct(Producto(id = producto.id, nombre = name, descripcion = description, color = colorHex, imagen = updatedImage))


                            navController.popBackStack()
                        } catch (e: Exception) {
                            errorMsg = "Error al actualizar la nota"
                            showErrorDialog = true
                        }

                    }

                } else {
                    errorMsg = "Nota no encontrada"
                    showErrorDialog = true
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
            Spacer(modifier = Modifier.width(8.dp)) to
                    Text("Actualizar Nota")
        }


    }

    if (showErrorDialog) {
        AlertaVacio(
            dialogTitle = "Error",
            dialogText = errorMsg,
            onDismissRequest = {
                showErrorDialog = false
            },
            onConfirmation = {
                showErrorDialog = false
            },
            show = showErrorDialog
        )
    }
   /* if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text("Selecciona una imagen, No debe pesar mas de 3 MB") },
            text = {
                ImagePickers(onImageSelected = { bitmap ->

                    showImagePickerDialog = false
                })
            },
            confirmButton = {
                Button(
                    onClick = { showImagePickerDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Cancelar")
                }
            }
        )
    } */
}
