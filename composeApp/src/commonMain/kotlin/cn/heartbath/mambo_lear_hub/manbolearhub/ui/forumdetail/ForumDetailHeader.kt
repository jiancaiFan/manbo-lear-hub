package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Forum
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.forumdetail.ForumDetailUiModel
import coil3.compose.AsyncImage

@Composable
internal fun ForumDetailHeader(
    header: ForumDetailUiModel.ForumHeader,
    onFollowClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(16.dp))
                .background(CommonColors.BackgroundWhite),
            contentAlignment = Alignment.Center
        ) {
            if (header.forumIconUrl.isNullOrBlank()) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(CommonColors.Placeholder),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Forum,
                        contentDescription = null,
                        tint = CommonColors.IconHint,
                        modifier = Modifier.size(26.dp)
                    )
                }
            } else {
                AsyncImage(
                    model = header.forumIconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = header.forumName.ifBlank { "Forum" },
                color = CommonColors.Title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "关注 ${header.todayPostCount ?: 0} · 帖子 ${header.totalThreadCount ?: 0}",
                color = CommonColors.Meta,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        val interactionSource = remember { MutableInteractionSource() }
        Box(
            modifier = Modifier
                .height(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(CommonColors.PrimaryBlue)
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(bounded = true),
                    role = Role.Button,
                    onClick = onFollowClick
                )
                .clearAndSetSemantics { contentDescription = "Follow forum" }
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+ 关注",
                color = CommonColors.BackgroundWhite,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}