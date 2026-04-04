package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ManBoLearHubViewModel : ViewModel() {

    private val _selectedTab = MutableStateFlow(ManBoMainTab.TAB_TYPE__HOME)
    val selectedTab: StateFlow<ManBoMainTab> = _selectedTab.asStateFlow()

    fun onTabSelected(tab: ManBoMainTab) {
        _selectedTab.value = tab
    }
}