package com.famas.frontendtask.feature_auth.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.famas.frontendtask.R
import com.famas.frontendtask.core.util.extensions.*

@Composable
fun SignInScreen(
    email: String,
    onEmail: (String) -> Unit,
    password: String,
    onPassword: (String) -> Unit,
    togglePassVisibility: () -> Unit,
    onLoginClick: () -> Unit,
    isPasswordVisible: Boolean
) {
    val context = LocalContext.current

    val textFieldColor = TextFieldDefaults.textFieldColors(
        backgroundColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )

    Scaffold(
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)

            ) {
                item {
                    60.height()
                    Text("Welcome Back", style = boldTextStyle(fontSize = 28.sp))
                    4.height()
                    Text("Log in to continue", style = secondaryTextStyle())
                    24.height()
                    Image(
                        painter = rememberImagePainter(data = R.drawable.ic_singn_in_logo),
                        contentScale = ContentScale.Crop,
                        contentDescription = "",
                        modifier = Modifier
                            .height(150.dp)
                            .width(150.dp)
                    )
                    32.height()
                    TextField(
                        value = email,
                        onValueChange = onEmail,
                        modifier = Modifier
                            .border(
                                0.5.dp,
                                shape = 4.radius(),
                                color = MaterialTheme.colors.primary
                            )
                            .fillMaxWidth(),
                        textStyle = primaryTextStyle(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        placeholder = {
                            Text(
                                "Enter Email",
                                style = secondaryTextStyle(fontSize = 16.sp)
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                    16.height()
                    TextField(
                        value = password,
                        onValueChange = onPassword,
                        modifier = Modifier
                            .border(
                                0.5.dp,
                                shape = 4.radius(),
                                color = MaterialTheme.colors.primary
                            )
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        textStyle = primaryTextStyle(),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        placeholder = {
                            Text(
                                "Enter Password",
                                style = secondaryTextStyle(fontSize = 16.sp)
                            )
                        },
                        colors = textFieldColor,
                        trailingIcon = {
                            IconButton(onClick = togglePassVisibility) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                    16.height()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Sign in",
                            style = boldTextStyle(fontSize = 22.sp),
                            textAlign = TextAlign.Center
                        )
                        16.width()
                        FloatingActionButton(
                            onClick = onLoginClick,
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp),
                            backgroundColor = MaterialTheme.colors.primary
                        )
                        {
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }

                    }
                    16.height()
                }
            }

        }
    )
}