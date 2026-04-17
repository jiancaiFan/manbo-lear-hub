package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors

@Composable
internal fun ForumDetailTabList(
    forumTabTitleList: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (forumTabTitleList.isEmpty()) return

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .selectableGroup(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(forumTabTitleList) { tabIndex, tabTitle ->
            val isSelectedTab = tabIndex == selectedTabIndex
            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelectedTab) CommonColors.PrimaryBlueLightBg else CommonColors.BackgroundWhite.copy(alpha = 0f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = if (isSelectedTab) 1.dp else 0.dp,
                        color = if (isSelectedTab) CommonColors.PrimaryBlue.copy(alpha = 0.25f) else CommonColors.DividerSoft.copy(alpha = 0f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .selectable(
                        selected = isSelectedTab,
                        onClick = { onTabSelected(tabIndex) },
                        role = Role.Tab
                    )
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = tabTitle,
                    color = if (isSelectedTab) CommonColors.PrimaryBlue else CommonColors.LeftTextNormal,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = if (isSelectedTab) FontWeight.Bold else FontWeight.SemiBold
                    )
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(CommonColors.DividerSoft.copy(alpha = 0.8f))
    )
}