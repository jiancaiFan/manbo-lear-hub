package cn.heartbath.mambo_lear_hub.manbolearhub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTabType
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.TabDisplayItem

// 与首页/板块列表风格统一
private val ColorPageBg = Color(0xFFF8FAFC)
private val ColorBar = Color.White
private val ColorActive = Color(0xFF2563EB)
private val ColorInactive = Color(0xFF9CA3AF)
private val ColorActiveBg = Color(0xFFEFF6FF)
private val ColorRipple = Color(0x332563EB)

@Composable
internal fun BottomNavigationBar(
    items: List<TabDisplayItem>,
    currentTab: ManBoMainTabType,
    onItemClick: (TabDisplayItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorPageBg)
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp + bottomInset)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(ColorBar)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentTab == item.tab

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (selected) ColorActiveBg else Color.Transparent)
                        .selectable(
                            selected = selected,
                            onClick = { onItemClick(item) },
                            role = Role.Tab,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(
                                bounded = true,
                                radius = 32.dp,
                                color = ColorRipple
                            )
                        )
                        .clearAndSetSemantics {
                            contentDescription = item.label
                            this.selected = selected
                        }
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp)
                            .clearAndSetSemantics {},
                        tint = if (selected) ColorActive else ColorInactive
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = item.label,
                        color = if (selected) ColorActive else ColorInactive,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                        maxLines = 1,
                        modifier = Modifier.clearAndSetSemantics {}
                    )
                }
            }
        }
    }
}