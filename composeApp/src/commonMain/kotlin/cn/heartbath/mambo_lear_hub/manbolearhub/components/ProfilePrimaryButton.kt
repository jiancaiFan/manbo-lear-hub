package cn.heartbath.mambo_lear_hub.manbolearhub.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun ProfilePrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState().value
    val scale = animateFloatAsState(
        targetValue = if (pressed) 0.985f else 1f,
        label = "profile_btn_scale"
    )

    val shape = RoundedCornerShape(999.dp)

    Button(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .scale(scale.value)
            .shadow(
                elevation = if (pressed) 10.dp else 20.dp, // 阴影更明显
                shape = shape,
                ambientColor = Color(0x40000000),
                spotColor = Color(0x33000000)
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF272A31),
                            Color(0xFF12141A)
                        )
                    )
                )
                .graphicsLayer { alpha = if (pressed) 0.95f else 1f },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = CommonColors.BackgroundWhite
            )
        }
    }
}