package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import androidx.compose.ui.graphics.Color

private val SelectedBg = Color(0xFFEFF6FF)
private val SelectedText = Color(0xFF2563EB)
private val NormalText = Color(0xFF6B7280)

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
            .selectableGroup(), // 关键：声明单选组
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEachIndexed { index, item ->
            val selected = index == selectedCategory

            Row(
                modifier = Modifier
                    .height(30.dp)
                    .background(
                        color = if (selected) SelectedBg else Color.Transparent,
                        shape = RoundedCornerShape(999.dp)
                    )
                    .selectable( // 关键：让系统读“已选中”，避免手写冗余状态
                        selected = selected,
                        onClick = { onSelectedCategory(index) },
                        role = Role.Tab
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    fontSize = 13.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) SelectedText else NormalText
                )
            }
        }
    }
}