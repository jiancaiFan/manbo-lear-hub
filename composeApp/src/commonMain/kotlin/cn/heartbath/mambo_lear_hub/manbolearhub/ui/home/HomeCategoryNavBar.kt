package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

private val ChipShape = RoundedCornerShape(999.dp)

@Composable
internal fun HomeCategoryNavBar(
    categories: List<HomeUIModel.CategoryItem>,
    selectedCategory: Int,
    onSelectedCategory: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .horizontalScroll(rememberScrollState())
            .selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEachIndexed { index, item ->
            val selected = index == selectedCategory

            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = 32.dp) // 替代固定高度，避免文字被挤
                    .background(
                        color = if (selected) CommonColors.PrimaryBlueLightBg else CommonColors.BackgroundWhite,
                        shape = ChipShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected) CommonColors.CardBorder else CommonColors.BorderLight,
                        shape = ChipShape
                    )
                    .selectable(
                        selected = selected,
                        onClick = { onSelectedCategory(index) },
                        role = Role.Tab
                    )
                    .padding(horizontal = 12.dp, vertical = 7.dp), // 稍加高，显示更完整
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    fontSize = 13.sp,
                    lineHeight = 16.sp, // 明确行高，避免不同平台字形裁切感
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) CommonColors.PrimaryBlue else CommonColors.Meta,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Clip // 单行标签不需要省略号
                )
            }
        }
    }
}