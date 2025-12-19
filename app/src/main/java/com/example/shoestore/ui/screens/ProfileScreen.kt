package com.example.shoestore.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoestore.R
import com.example.shoestore.ui.viewmodels.ProfileUiState
import com.example.shoestore.ui.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val uiState = viewModel.uiState

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let { viewModel.onPhotoCaptured(it) }
    }

    LaunchedEffect(Unit) { viewModel.loadProfile() }

    var showLoyaltyDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.profile),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF48B2E7))
                        .clickable { viewModel.isEditing = !viewModel.isEditing },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = stringResource(R.string.settings),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Avatar
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
                        contentDescription = stringResource(R.string.change_profile_picture),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.profile),
                        contentDescription = stringResource(R.string.profile),
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name
            Text(
                text = "${viewModel.name} ${viewModel.lastName}",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loyalty card block
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
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(x = (-10).dp)
                ) {
                    Text(
                        text = stringResource(R.string.open),
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = Color.Gray,
                        modifier = Modifier
                            .rotate(-90f)
                            .width(50.dp)
                            .height(20.dp)
                    )
                }

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
                            .clickable { showLoyaltyDialog = true }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.str1),
                            contentDescription = stringResource(R.string.loyalty_card),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Editable fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EditableField(
                    label = stringResource(R.string.your_name),
                    value = viewModel.name,
                    isEditable = viewModel.isEditing
                ) { viewModel.name = it }

                EditableField(
                    label = stringResource(R.string.last_name),
                    value = viewModel.lastName,
                    isEditable = viewModel.isEditing
                ) { viewModel.lastName = it }

                EditableField(
                    label = stringResource(R.string.address),
                    value = viewModel.address,
                    isEditable = viewModel.isEditing
                ) { viewModel.address = it }

                EditableField(
                    label = stringResource(R.string.phone_number),
                    value = viewModel.phone,
                    isEditable = viewModel.isEditing
                ) { viewModel.phone = it }
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                        text = stringResource(R.string.save_changes),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        // Loading state
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

        // Error state
        if (uiState is ProfileUiState.Error) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissError() },
                confirmButton = {
                    TextButton(onClick = { viewModel.dismissError() }) {
                        Text(stringResource(R.string.ok))
                    }
                },
                title = { Text(stringResource(R.string.error)) },
                text = { Text(uiState.message) }
            )
        }

        // Loyalty dialog
        if (showLoyaltyDialog) {
            AlertDialog(
                onDismissRequest = { showLoyaltyDialog = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(20.dp),
                title = {},
                text = {
                    // только картинка, без лишних отступов
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(420.dp),        // диалог стал больше по высоте
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 8.dp) // чуть‑чуть, чтобы не прилипала к краю
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.str1),
                                contentDescription = stringResource(R.string.loyalty_card),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .rotate(90f),      // или -90f, если нужно в другую сторону
                                contentScale = ContentScale.Fit   // крупно, но без обрезания
                            )
                        }
                    }
                },
                confirmButton = {
                    // только одна кнопка снизу на всю ширину
                    Button(
                        onClick = { showLoyaltyDialog = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF48B2E7)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            color = Color.White
                        )
                    }
                }
            )
        }


    }
}

@Composable
fun EditableField(
    label: String,
    value: String,
    isEditable: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
        if (isEditable) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFE0E0E0),
                    unfocusedBorderColor = Color(0xFFE0E0E0)
                )
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(Color(0xFFF7F7F9), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = value)
            }
        }
    }
}
