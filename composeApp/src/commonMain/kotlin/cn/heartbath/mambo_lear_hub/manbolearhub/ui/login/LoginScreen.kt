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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ManBoTopActionBar
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ProfilePrimaryButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: (account: String, password: String, autoLogin: Boolean) -> Unit = { _, _, _ -> },
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onWechatLoginClick: () -> Unit = {}
) {
    var account by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var autoLogin by remember { mutableStateOf(false) }

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
                account = account,
                password = password,
                autoLogin = autoLogin,
                onAccountChange = { account = it },
                onPasswordChange = { password = it },
                onAutoLoginChange = { autoLogin = it },
                onForgotPasswordClick = onForgotPasswordClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfilePrimaryButton(
                text = "立即认证并登录",
                onClick = { onLoginClick(account, password, autoLogin) }
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
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
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