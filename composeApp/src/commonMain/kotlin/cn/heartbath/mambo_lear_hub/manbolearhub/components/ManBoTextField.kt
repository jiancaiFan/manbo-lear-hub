package cn.heartbath.mambo_lear_hub.manbolearhub.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun ManBoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(14.dp)
    val textStyle = MaterialTheme.typography.bodyMedium.copy(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = hint,
                color = CommonColors.IconHint,
                style = textStyle
            )
        },
        keyboardOptions = keyboardOptions,
        shape = shape,
        textStyle = textStyle.copy(color = CommonColors.Title),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = CommonColors.HomePageBg,
            unfocusedContainerColor = CommonColors.HomePageBg,
            disabledContainerColor = CommonColors.HomePageBg,
            focusedIndicatorColor = CommonColors.HomePageBg,
            unfocusedIndicatorColor = CommonColors.HomePageBg,
            disabledIndicatorColor = CommonColors.HomePageBg,
            focusedTextColor = CommonColors.Title,
            unfocusedTextColor = CommonColors.Title
        )
    )
}