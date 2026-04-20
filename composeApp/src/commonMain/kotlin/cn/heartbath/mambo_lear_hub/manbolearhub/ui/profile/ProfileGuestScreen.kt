package cn.heartbath.mambo_lear_hub.manbolearhub.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.ProfilePrimaryButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun ProfileGuestScreen(
    onLoginClick: () -> Unit = {},
    onGuestClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CommonColors.BackgroundWhite)
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(26.dp))

        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(CommonColors.Title),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                CommonColors.BottomBarItemSelected,
                                Color(0xFF1D4ED8)
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "探索极客世界",
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            color = CommonColors.Title
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "加入 50k+ 开发者，在极客社区开启\n你的技术进化之旅",
            fontSize = 14.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium,
            color = CommonColors.Title,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        ProfileFeatureCard(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ChatBubbleOutline,
                    contentDescription = null,
                    tint = CommonColors.BottomBarItemSelected
                )
            },
            title = "深度技术讨论",
            desc = "与全球顶尖开发者碰撞火花"
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProfileFeatureCard(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.CardGiftcard,
                    contentDescription = null,
                    tint = Color(0xFF8B5CF6)
                )
            },
            title = "精选职场机会",
            desc = "解锁技术大厂内推专属通道"
        )

        Spacer(modifier = Modifier.height(26.dp))

        ProfilePrimaryButton(text = "立即认证并加入", onClick = onLoginClick)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "先随便看看",
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onGuestClick
            ),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = CommonColors.LeftTextNormal
        )

        Spacer(modifier = Modifier.height(12.dp))

        AvatarGroup()

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "全球领先极客社区",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = CommonColors.LeftTextNormal
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ProfileFeatureCard(
    icon: @Composable () -> Unit,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CommonColors.BackgroundWhite)
            .border(0.6.dp, CommonColors.CardBorder, RoundedCornerShape(16.dp))
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CommonColors.HomePageBg),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Spacer(modifier = Modifier.size(12.dp))

        Column {
            Text(
                text = title,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = CommonColors.Title
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.Normal,
                color = CommonColors.Meta
            )
        }
    }
}

@Composable
private fun AvatarGroup() {
    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .offset(x = (index * -8).dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        when (index) {
                            0 -> Color(0xFFE9A07A)
                            1 -> Color(0xFF8CB1D9)
                            2 -> Color(0xFF7A7A85)
                            else -> Color(0xFFD0A18E)
                        }
                    )
                    .border(1.dp, CommonColors.BackgroundWhite, CircleShape)
            )
        }

        Box(
            modifier = Modifier
                .offset(x = (-32).dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(CommonColors.Placeholder)
                .border(1.dp, CommonColors.BackgroundWhite, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+50k",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = CommonColors.Meta
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ProfileGuestScreenPreview() {
    ProfileGuestScreen()
}