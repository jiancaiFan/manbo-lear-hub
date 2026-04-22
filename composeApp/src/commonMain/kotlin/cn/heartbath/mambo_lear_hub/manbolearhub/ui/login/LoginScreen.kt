package cn.heartbath.mambo_lear_hub.manbolearhub.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ManBoTopActionBar
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ProfilePrimaryButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.login.LoginViewModel
import co.touchlab.kermit.Logger
import org.koin.compose.koinInject

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = koinInject(),
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onWechatLoginClick: () -> Unit = {}
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            //onBackClick()
            loginState.loginRaw?.let {
                Logger.d("Login successful, raw data: $it")
            }
        }
    }

    Scaffold(
        containerColor = CommonColors.BackgroundWhite,
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
        topBar = {
            ManBoTopActionBar(
                title = "",
                onBackClick = onBackClick,
                backIcon = Icons.AutoMirrored.Outlined.ArrowBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CommonColors.BackgroundWhite)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            LoginHeaderSection()

            Spacer(modifier = Modifier.height(20.dp))

            LoginFormSection(
                username = username,
                password = password,
                viewModel = viewModel,
                onForgotPasswordClick = onForgotPasswordClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (loginState.isError && !loginState.errorMessage.isNullOrBlank()) {
                Text(
                    text = loginState.errorMessage!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (loginState.isSuccess) {
                Text(
                    text = "登录成功",
                    color = CommonColors.PrimaryBlue,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            ProfilePrimaryButton(
                text = if (loginState.isLoading) "登录中..." else "立即认证并登录",
                enabled = !loginState.isLoading,
                onClick = { viewModel.onLogin() }
            )

            Spacer(modifier = Modifier.height(14.dp))

            LoginBottomSection(
                onRegisterClick = onRegisterClick,
                onWechatLoginClick = onWechatLoginClick
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LoginHeaderSection() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "登录",
            color = CommonColors.Title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .height(28.dp)
                .width(6.dp)
                .background(CommonColors.PrimaryBlue, shape = RoundedCornerShape(999.dp))
        )
    }

    Text(
        text = "探索技术的无限可能",
        color = CommonColors.Meta,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier.padding(top = 8.dp)
    )
}