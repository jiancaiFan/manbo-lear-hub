package cn.heartbath.mambo_lear_hub.manbolearhub.ui.register

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
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
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ManBoTextField
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun RegisterFormSection(
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    agreed: Boolean,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onAgreedChange: (Boolean) -> Unit,
    onUserAgreementClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit
) {
    val cardShape = RoundedCornerShape(16.dp)
    val labelStyle = MaterialTheme.typography.labelMedium.copy(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, cardShape, clip = false)
            .clip(cardShape)
            .background(CommonColors.BackgroundWhite)
            .border(1.dp, CommonColors.DividerSoft.copy(alpha = 0.65f), cardShape)
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Text(text = "用户名", color = CommonColors.LeftTextNormal, style = labelStyle)
        Spacer(modifier = Modifier.height(8.dp))
        ManBoTextField(
            value = username,
            onValueChange = onUsernameChange,
            hint = "设置您的极客身份",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = { Icon(Icons.Outlined.PersonOutline, null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "电���邮箱", color = CommonColors.LeftTextNormal, style = labelStyle)
        Spacer(modifier = Modifier.height(8.dp))
        ManBoTextField(
            value = email,
            onValueChange = onEmailChange,
            hint = "verification@geek.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Outlined.Email, null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "设置密码", color = CommonColors.LeftTextNormal, style = labelStyle)
        Spacer(modifier = Modifier.height(8.dp))
        ManBoTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = "8-16位安全密钥",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = CommonColors.IconHint) },
            trailingIcon = { Icon(Icons.Outlined.VisibilityOff, null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "确认密码", color = CommonColors.LeftTextNormal, style = labelStyle)
        Spacer(modifier = Modifier.height(8.dp))
        ManBoTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            hint = "再次确认您的密码",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = CommonColors.IconHint) },
            trailingIcon = { Icon(Icons.Outlined.VisibilityOff, null, tint = CommonColors.IconHint) }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreed,
                onCheckedChange = onAgreedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = CommonColors.PrimaryBlue,
                    uncheckedColor = CommonColors.IconHint,
                    checkmarkColor = CommonColors.BackgroundWhite
                )
            )

            Text(
                text = "我已阅读并同意 ",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Text(
                text = "用户协议",
                color = CommonColors.PrimaryBlue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false),
                    role = Role.Button,
                    onClick = onUserAgreementClick
                )
            )

            Text(
                text = " 与 ",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Text(
                text = "隐私政策",
                color = CommonColors.PrimaryBlue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false),
                    role = Role.Button,
                    onClick = onPrivacyPolicyClick
                )
            )
        }
    }
}