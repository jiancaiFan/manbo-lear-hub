package cn.heartbath.manbolearhub.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.manbolearhub.ui.ManBoMainTabType
import cn.heartbath.manbolearhub.ui.TabDisplayItem

@Composable
internal fun BottomNavigationBar(
    items: List<TabDisplayItem>,
    currentTab: ManBoMainTabType,
    onItemClick: (TabDisplayItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CommonColors.BackgroundWhite)
            .drawBehind {
                val shadowHeight = 2.dp.toPx()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x1A000000), // 底部更深（约10%）
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = -shadowHeight
                    ),
                    topLeft = Offset(0f, 0f - shadowHeight),
                    size = androidx.compose.ui.geometry.Size(size.width, shadowHeight)
                )
            }
    ) {
        HorizontalDivider(
            thickness = 0.5.dp,
            color = CommonColors.BorderLight
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(56.dp)
                .background(CommonColors.BackgroundWhite)
                .selectableGroup(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentTab == item.tab

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .selectable(
                            selected = selected,
                            onClick = { onItemClick(item) },
                            role = Role.Tab,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(
                                bounded = true,
                                radius = 48.dp,
                                color = CommonColors.RippleOverlay
                            )
                        )
                        .clearAndSetSemantics {
                            contentDescription = item.label
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp)
                            .clearAndSetSemantics {},
                        tint = if (selected) CommonColors.BrandPrimaryRed else CommonColors.TextSecondary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.label,
                        color = if (selected) CommonColors.BrandPrimaryRed else CommonColors.TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                        maxLines = 1,
                        modifier = Modifier.clearAndSetSemantics {}
                    )
                }
            }
        }
    }
}