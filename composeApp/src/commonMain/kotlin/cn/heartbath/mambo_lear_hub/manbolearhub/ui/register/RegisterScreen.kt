package cn.heartbath.mambo_lear_hub.manbolearhub.ui.register

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ManBoTopActionBar
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun RegisterScreen(
    onBackClick: () -> Unit,
    onCreateAccountClick: (username: String, email: String, password: String, confirmPassword: String, agreed: Boolean) -> Unit = { _, _, _, _, _ -> },
    onUserAgreementClick: () -> Unit = {},
    onPrivacyPolicyClick: () -> Unit = {},
    onGoLoginClick: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }

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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "注册",
                    color = CommonColors.Title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .size(width = 6.dp, height = 28.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(CommonColors.PrimaryBlue)
                )
            }

            Text(
                text = "加入开发者精英圈层",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            RegisterFormSection(
                username = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                agreed = agreed,
                onUsernameChange = { username = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onConfirmPasswordChange = { confirmPassword = it },
                onAgreedChange = { agreed = it },
                onUserAgreementClick = onUserAgreementClick,
                onPrivacyPolicyClick = onPrivacyPolicyClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegisterBottomSection(
                onCreateAccountClick = {
                    onCreateAccountClick(username, email, password, confirmPassword, agreed)
                },
                onGoLoginClick = onGoLoginClick
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}