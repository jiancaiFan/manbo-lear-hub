package cn.heartbath.mambo_lear_hub.manbolearhub.ui.forumdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cn.heartbath.mambo_lear_hub.manbolearhub.constants.CommonColors
import cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel.forumdetail.ForumDetailViewModel
import org.koin.compose.koinInject

@Composable
internal fun ForumDetailScreen(
    forumId: Int?,
    onBackToHome: () -> Unit,
    viewModel: ForumDetailViewModel = koinInject(),
) {
    val state by viewModel.forumDetailState.collectAsState()
    val ui = state.forumDetailUIModel

    LaunchedEffect(forumId) {
        viewModel.fetchForumDetail(forumId)
    }

    Scaffold(
        containerColor = CommonColors.HomePageBg,
        topBar = {
            ForumTopActionBar(
                title = ui.header.forumName.ifBlank { "Apple" },
                onBack = onBackToHome,
                onSearchClick = {},
                onShareClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: 发帖 */ },
                shape = CircleShape,
                containerColor = CommonColors.PrimaryBlue
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    ) { innerPadding ->
        ForumDetailContent(
            state = state,
            onTabSelected = viewModel::onTabSelected,
            modifier = Modifier
                .fillMaxSize()
                .background(CommonColors.HomePageBg)
                .padding(innerPadding)
        )
    }
}