package cn.heartbath.mambo_lear_hub.manbolearhub.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun HomeCategoryContent(
    selectedCategoryId: String,
    data: List<String>,
    isLoading: Boolean,
    errorMessage: String?
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        /**
         * 不同页面可以这样写：
         *   when (selectedCategoryId) {
         *         "1" -> RecommendPage(data, isLoading, errorMessage)
         *         "2" -> AndroidPage(data, isLoading, errorMessage)
         *         "3" -> ApplePage(data, isLoading, errorMessage)
         *         else -> CommonCategoryPage(data, isLoading, errorMessage)
         *     }
         */

        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            errorMessage != null -> {
                Text(text = errorMessage)
            }

            data.isEmpty() -> {
                Text(text = "暂无数据")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(data.size) { index ->
                        Text(text = data[index])
                        Text(text = "当前分类: $selectedCategoryId")
                    }
                }
            }
        }
    }
}