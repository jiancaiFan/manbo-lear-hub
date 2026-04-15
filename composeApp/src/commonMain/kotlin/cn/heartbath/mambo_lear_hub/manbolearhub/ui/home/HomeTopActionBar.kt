package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private val ColorCard = Color.White
private val ColorIconBg = Color(0xFFF1F5F9)
private val ColorSearchBg = Color(0xFFF8FAFC)
private val ColorPrimary = Color(0xFF2563EB)
private val ColorTextPrimary = Color(0xFF111827)
private val ColorTextHint = Color(0xFF9CA3AF)
private val ColorBadge = Color(0xFFEF4444)

@Composable
fun HomeTopActionBar(
    searchHint: String = "搜索你感兴趣的内容",
    unreadCount: Int? = null,
    onProfileClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onQuickActionClick: () -> Unit = {},
) {
    val noRipple = null
    val interaction = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x22000000),
                spotColor = Color(0x18000000)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(ColorCard)
            .padding(horizontal = 10.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // 左侧头像按钮
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ColorIconBg)
                .clickable(
                    interactionSource = interaction,
                    indication = ripple(bounded = true, radius = 22.dp, color = Color(0x332563EB)),
                    role = Role.Button,
                    onClick = onProfileClick
                )
                .clearAndSetSemantics { contentDescription = "Profile" },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null,
                tint = ColorPrimary
            )
        }

        Spacer(Modifier.width(10.dp))

        // 中间搜索栏
        Row(
            modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(19.dp))
                .background(ColorSearchBg)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = noRipple,
                    role = Role.Button,
                    onClick = onSearchClick
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = ColorTextHint
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = searchHint,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ColorTextHint,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.width(10.dp))

        // 消息按钮 + 角标
        Box {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(ColorIconBg)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true, radius = 22.dp, color = Color(0x332563EB)),
                        role = Role.Button,
                        onClick = onMessageClick
                    )
                    .clearAndSetSemantics { contentDescription = "Messages" },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = ColorTextPrimary
                )
            }

            unreadCount?.takeIf { it > 0 }?.let {
                val badgeText = if (it > 99) "99+" else it.toString()
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp),
                    containerColor = ColorBadge,
                    contentColor = Color.White
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        // 右侧快捷入口
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ColorIconBg)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, radius = 22.dp, color = Color(0x332563EB)),
                    role = Role.Button,
                    onClick = onQuickActionClick
                )
                .clearAndSetSemantics { contentDescription = "Quick actions" },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = ColorTextPrimary
            )
        }
    }
}