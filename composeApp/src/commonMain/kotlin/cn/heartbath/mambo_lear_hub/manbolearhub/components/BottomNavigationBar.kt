package cn.heartbath.mambo_lear_hub.manbolearhub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .background(Brush.verticalGradient(listOf(Color.White, Color(0xFFF9FBFF))))
            .border(1.dp, Color(0xFFDDE3EC), barShape)
            .padding(7.dp)
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val selected = currentTab == item.tab
            Column(
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 42.dp)
                    .clip(itemShape)
                    .background(if (selected) Color(0xFFEFF4FF) else Color.Transparent)
                    .selectable(selected = selected, onClick = { onItemClick(item) })
                    .padding(vertical = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    modifier = Modifier.size(18.dp),
                    tint = if (selected) Color(0xFF2F6BFF) else Color(0xFF8B95A7)
                )
                Text(
                    text = item.label,
                    fontSize = 10.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                    color = if (selected) Color(0xFF2F6BFF) else Color(0xFF8B95A7),
                    maxLines = 1
                )
            }
        }
    }
}