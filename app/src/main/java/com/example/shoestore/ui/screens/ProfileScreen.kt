package com.example.shoestore.ui.screens
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.layout.offset
import android.graphics.Bitmap
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.R
import com.example.shoestore.ui.viewmodels.ProfileViewModel
import com.example.shoestore.ui.viewmodels.ProfileUiState

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val uiState = viewModel.uiState

    // Лаунчер для камеры
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { viewModel.onPhotoCaptured(it) }
    }

    LaunchedEffect(Unit) { viewModel.loadProfile() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Заголовок
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Профиль",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                // Кнопка редактирования с синим кружком
                Box(
                    modifier = Modifier
                        .size(30.dp) // Размер кружка
                        .clip(CircleShape) // Делаем круглую форму
                        .background(Color(0xFF48B2E7)) // Цвет фона #F7F7F9
                        .clickable { viewModel.isEditing = !viewModel.isEditing }
                        .padding(8.dp), // Внутренние отступы для иконки
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "Редактировать",
                        tint = Color.White // Черный цвет иконки
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Аватар
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0))
                    .clickable { cameraLauncher.launch(null) }
            ) {
                if (viewModel.bitmapPhoto != null) {
                    Image(
                        bitmap = viewModel.bitmapPhoto!!.asImageBitmap(),
                        contentDescription = "Фото профиля",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.profile),
                        contentDescription = "Иконка профиля",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Имя пользователя
            Text(
                text = "${viewModel.name} ${viewModel.lastName}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE5E5E5),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Надпись "Открыть" вертикально слева
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = (-10).dp) // Сдвигаем немного влево для лучшего позиционирования
                ) {
                    Text(
                        text = "Открыть",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = Color.Gray,
                        modifier = Modifier
                            .rotate(-90f) // Поворачиваем текст на -90 градусов (вертикально)
                            .width(50.dp)
                            .height(20.dp)
                    )
                }

                // Штрих-код по центру
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(300.dp)
                            .height(65.dp)
                            .background(Color(0xFFF8F8F8), RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.str1),
                            contentDescription = "Штрих-код",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Поля для редактирования
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EditableField("Имя", viewModel.name, viewModel.isEditing) { viewModel.name = it }
                EditableField("Фамилия", viewModel.lastName, viewModel.isEditing) { viewModel.lastName = it }
                EditableField("Адрес", viewModel.address, viewModel.isEditing) { viewModel.address = it }
                EditableField("Телефон", viewModel.phone, viewModel.isEditing) { viewModel.phone = it }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка сохранения
            if (viewModel.isEditing) {
                Button(
                    onClick = { viewModel.saveProfile() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3)
                    )
                ) {
                    Text(
                        "Сохранить изменения",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        // Состояние загрузки
        if (uiState is ProfileUiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        // Состояние ошибки
        if (uiState is ProfileUiState.Error) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissError() },
                confirmButton = {
                    TextButton(onClick = { viewModel.dismissError() }) {
                        Text("OK")
                    }
                },
                title = { Text("Ошибка") },
                text = { Text(uiState.message) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableField(label: String, value: String, isEditable: Boolean, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        if (isEditable) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFFF7F7F9), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = value)
            }
        }
    }
}