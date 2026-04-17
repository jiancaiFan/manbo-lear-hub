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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.GlobalActionButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
fun HomeTopActionBar(
    searchHint: String = "搜索你感兴趣的内容",
    unreadCount: Int? = null,
    onProfileClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onQuickActionClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = CommonColors.TopCardShadowAmbient,
                spotColor = CommonColors.TopCardShadowSpot
            )
            .clip(RoundedCornerShape(16.dp))
            .background(CommonColors.BackgroundWhite)
            .padding(horizontal = 10.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        GlobalActionButton(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Profile",
            onClick = onProfileClick
        )

        Spacer(Modifier.width(10.dp))

        Row(
            modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(19.dp))
                .background(CommonColors.HomePageBg)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Button,
                    onClick = onSearchClick
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = CommonColors.IconHint
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = searchHint,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = CommonColors.LeftTextNormal,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.width(10.dp))

        Box {
            GlobalActionButton(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Messages",
                onClick = onMessageClick
            )

            unreadCount?.takeIf { it > 0 }?.let {
                val badgeText = if (it > 99) "99+" else it.toString()
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp),
                    containerColor = CommonColors.BadgeRed,
                    contentColor = CommonColors.BackgroundWhite
                ) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(Modifier.width(8.dp))

        GlobalActionButton(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Quick actions",
            onClick = onQuickActionClick
        )
    }
}