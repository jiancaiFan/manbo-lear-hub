package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel

private val ColorCard = Color.White
private val ColorChipBg = Color(0xFFF8FAFC)
private val ColorSelectedBg = Color(0xFFEFF6FF)
private val ColorSelectedText = Color(0xFF2563EB)
private val ColorNormalText = Color(0xFF6B7280)

@Composable
internal fun HomeCategoryNavBar(
    categories: List<HomeUIModel.CategoryItem>,
    selectedCategory: Int,
    onSelectedCategory: (Int) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0x22000000),
                spotColor = Color(0x18000000)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(ColorCard)
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEachIndexed { position, item ->
            val selected = position == selectedCategory

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(if (selected) ColorSelectedBg else ColorChipBg)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            bounded = true,
                            color = Color(0x332563EB)
                        ),
                        onClick = { onSelectedCategory(position) }
                    )
                    .semantics {
                        this.selected = selected
                        role = Role.RadioButton
                    }
                    .wrapContentWidth()
                    .height(34.dp)
                    .padding(horizontal = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                    color = if (selected) ColorSelectedText else ColorNormalText,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.padding(end = 2.dp))
    }
}