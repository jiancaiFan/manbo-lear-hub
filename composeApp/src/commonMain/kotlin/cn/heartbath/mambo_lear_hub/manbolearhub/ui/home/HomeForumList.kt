@file:Suppress("DuplicatedCode")

package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.heartbath.mambo_lear_hub.manbolearhub.model.home.HomeUIModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

private val PageBg = Color(0xFFF8FAFC)
private val CardBg = Color.White
private val CardBorder = Color(0xFFEFF2F6)
private val TitleColor = Color(0xFF111827)
private val MetaColor = Color(0xFF6B7280)
private val DescColor = Color(0xFF4B5563)

private val LeftNormalText = Color(0xFF9CA3AF)
private val LeftSelectedText = Color(0xFF2563EB)
private val LeftSelectedBg = Color(0xFFEFF6FF)
private val LeftIndicator = Color(0xFF2563EB)

private const val SCROLL_TRIGGER_PX = 24

@Composable
internal fun HomeForumList(
    forumCategories: List<HomeUIModel.ForumCategory>,
    selectedForumCategory: Int,
    onSelectedForumCategory: (Int) -> Unit,
    navigateToForumDetail: (Int) -> Unit,
    onScrollDirectionChanged: (Boolean) -> Unit = {},
) {
    val rightListState = rememberLazyListState()
    var lastIndex by remember { mutableIntStateOf(0) }
    var lastOffset by remember { mutableIntStateOf(0) }
    var accumulatedDelta by remember { mutableIntStateOf(0) }

    LaunchedEffect(rightListState, selectedForumCategory) {
        snapshotFlow { rightListState.firstVisibleItemIndex to rightListState.firstVisibleItemScrollOffset }
            .mapNotNull { (index, offset) ->
                val delta = when {
                    index > lastIndex -> SCROLL_TRIGGER_PX
                    index < lastIndex -> -SCROLL_TRIGGER_PX
                    else -> offset - lastOffset
                }

                lastIndex = index
                lastOffset = offset
                if (delta == 0) return@mapNotNull null

                accumulatedDelta += delta
                when {
                    accumulatedDelta >= SCROLL_TRIGGER_PX -> {
                        accumulatedDelta = 0
                        true
                    }
                    accumulatedDelta <= -SCROLL_TRIGGER_PX -> {
                        accumulatedDelta = 0
                        false
                    }
                    else -> null
                }
            }
            .distinctUntilChanged()
            .collectLatest(onScrollDirectionChanged)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(0.30f)
                .fillMaxHeight()
                .drawBehind {
                    drawLine(
                        color = Color(0xFFE5E7EB),
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                .selectableGroup()
        ) {
            itemsIndexed(
                items = forumCategories,
                key = { index, category -> "${category.categoryName}_$index" }
            ) { index, category ->
                val selected = selectedForumCategory == index
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(if (selected) LeftSelectedBg else Color.Transparent)
                        .selectable(
                            selected = selected,
                            onClick = { onSelectedForumCategory(index) },
                            role = Role.Tab
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(42.dp)
                            .background(if (selected) LeftIndicator else Color.Transparent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = category.categoryName,
                        fontSize = 12.sp,
                        color = if (selected) LeftSelectedText else LeftNormalText,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        LazyColumn(
            state = rightListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(PageBg),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = forumCategories.getOrNull(selectedForumCategory)?.forumList.orEmpty(),
                key = { forum -> "${forum.forumId}_${forum.forumName}" }
            ) { forum ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateToForumDetail(forum.forumId.toIntOrNull() ?: 0) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(0.6.dp, CardBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 11.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AsyncImage(
                            model = forum.iconUrl.orEmpty(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(Color(0xFFE5E7EB)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = forum.forumName,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = TitleColor,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Row(
                                modifier = Modifier.padding(top = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "主题 ${forum.threadCount}",
                                    fontSize = 11.sp,
                                    color = MetaColor
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "贴数 ${forum.postCount}",
                                    fontSize = 11.sp,
                                    color = MetaColor
                                )
                            }

                            if (!forum.forumDescription.isNullOrBlank()) {
                                Text(
                                    text = forum.forumDescription,
                                    modifier = Modifier.padding(top = 3.dp),
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    color = DescColor
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color(0xFFD1D5DB)
                        )
                    }
                }
            }
        }
    }
}