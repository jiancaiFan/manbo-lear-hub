package cn.heartbath.mambo_lear_hub.manbolearhub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTabType
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.TabDisplayItem

@Composable
internal fun BottomNavigationBar(
    items: List<TabDisplayItem>,
    currentTab: ManBoMainTabType,
    onItemClick: (TabDisplayItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val barShape = RoundedCornerShape(34.dp)
    val itemShape = RoundedCornerShape(22.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = bottom + 6.dp)
            .shadow(12.dp, barShape)
            .clip(barShape)
            .background(
                Brush.verticalGradient(
                    listOf(
                        CommonColors.BottomBarGradientTop,
                        CommonColors.BottomBarGradientBottom
                    )
                )
            )
            .border(1.dp, CommonColors.BottomBarBorder, barShape)
            .padding(7.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = currentTab == item.tab
            val itemColor = if (selected) {
                CommonColors.BottomBarItemSelected
            } else {
                CommonColors.BottomBarItemUnselected
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 42.dp)
                    .clip(itemShape)
                    .background(
                        if (selected) CommonColors.BottomBarItemSelectedBg
                        else CommonColors.BackgroundWhite.copy(alpha = 0f)
                    )
                    .selectable(
                        selected = selected,
                        onClick = { onItemClick(item) },
                        role = Role.Tab
                    )
                    .padding(vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null, // ���免与文本重复朗读
                    modifier = Modifier
                        .size(18.dp)
                        .clearAndSetSemantics { }, // 清掉图标自身语义
                    tint = itemColor
                )
                Text(
                    text = item.label,
                    fontSize = 10.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    color = itemColor,
                    maxLines = 1
                )
            }
        }
    }
}