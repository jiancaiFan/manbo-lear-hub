package cn.heartbath.mambo_lear_hub.manbolearhub.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ManBoTextField
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun LoginFormSection(
    account: String,
    password: String,
    autoLogin: Boolean,
    onAccountChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAutoLoginChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, cardShape, clip = false)
            .clip(cardShape)
            .background(CommonColors.BackgroundWhite)
            .border(1.dp, CommonColors.DividerSoft.copy(alpha = 0.65f), cardShape)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Text(
            text = "账户通行证",
            color = CommonColors.LeftTextNormal,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        ManBoTextField(
            value = account,
            onValueChange = onAccountChange,
            hint = "手机号 / 邮箱 / 用户名",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.PersonOutline, contentDescription = null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "安全密码",
            color = CommonColors.LeftTextNormal,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        ManBoTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = "请输入您的密码",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null, tint = CommonColors.IconHint) },
            trailingIcon = { Icon(Icons.Outlined.VisibilityOff, contentDescription = null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true),
                    role = Role.Checkbox
                ) { onAutoLoginChange(!autoLogin) }
            ) {
                Checkbox(
                    checked = autoLogin,
                    onCheckedChange = onAutoLoginChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = CommonColors.PrimaryBlue,
                        uncheckedColor = CommonColors.IconHint,
                        checkmarkColor = CommonColors.BackgroundWhite
                    )
                )
                Text(
                    text = "自动登录",
                    color = CommonColors.Meta,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "忘记密码?",
                color = CommonColors.PrimaryBlue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false),
                    role = Role.Button,
                    onClick = onForgotPasswordClick
                )
            )
        }
    }
}