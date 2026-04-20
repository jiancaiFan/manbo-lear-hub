package cn.heartbath.mambo_lear_hub.manbolearhub.ui.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ProfilePrimaryButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun RegisterBottomSection(
    onCreateAccountClick: () -> Unit,
    onGoLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfilePrimaryButton(
            text = "创建极客账号",
            onClick = onCreateAccountClick
        )

        Spacer(modifier = Modifier.height(14.dp)) // 与 Login 页面保持一致

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "已有账号? ",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = "立即登录",
                color = CommonColors.PrimaryBlue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false),
                    role = Role.Button,
                    onClick = onGoLoginClick
                )
            )
        }
    }
}