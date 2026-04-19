package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.components.GlobalActionButton
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun ForumTopActionBar(
    title: String,
    onBack: () -> Unit,
    onSearchClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlobalActionButton(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Back",
            onClick = onBack
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = title,
            color = CommonColors.Title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlobalActionButton(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search",
                onClick = onSearchClick
            )

            GlobalActionButton(
                imageVector = Icons.Outlined.IosShare,
                contentDescription = "Share",
                onClick = onShareClick
            )
        }
    }
}