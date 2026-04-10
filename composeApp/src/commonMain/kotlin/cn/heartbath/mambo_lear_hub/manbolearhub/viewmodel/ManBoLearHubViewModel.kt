package cn.heartbath.mambo_lear_hub.manbolearhub.viewmodel

import androidx.lifecycle.ViewModel
import cn.heartbath.mambo_lear_hub.manbolearhub.ui.ManBoMainTabType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ManBoLearHubViewModel : ViewModel() {

    private val _selectedTab = MutableStateFlow(ManBoMainTabType.HOME)
    val selectedTab: StateFlow<ManBoMainTabType> = _selectedTab.asStateFlow()

    fun onTabSelected(tab: ManBoMainTabType) {
        _selectedTab.value = tab
    }
}