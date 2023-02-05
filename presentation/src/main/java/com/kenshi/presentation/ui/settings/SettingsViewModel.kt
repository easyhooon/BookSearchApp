package com.kenshi.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.kenshi.domain.usecase.GetCacheDeleteModeUseCase
import com.kenshi.domain.usecase.GetSortModeUseCase
import com.kenshi.domain.usecase.SaveCacheDeleteModeUseCase
import com.kenshi.domain.usecase.SaveSortModeUseCase
import com.kenshi.presentation.worker.CacheDeleteWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveSortModeUseCase: SaveSortModeUseCase,
    private val getSortModeUseCase: GetSortModeUseCase,
    private val saveCacheDeleteModeUseCase: SaveCacheDeleteModeUseCase,
    private val getCacheDeleteModeUseCase: GetCacheDeleteModeUseCase,
    private val workManager: WorkManager,
) : ViewModel() {

    fun saveSortMode(value: String) = viewModelScope.launch {
        saveSortModeUseCase(value)
    }

    // 설정 값 특성상 전체 데이터 스트림을 구독할 필요x
    // flow 에서 단일 스트링값을 가져오기 위해 .first()
    // withContext(Dispatchers.IO) 디스패처의 종류와는 상관없이 withContext 블럭은 반드시 값을 반환하고 종료한다고 한다
    suspend fun getSortMode() = withContext(viewModelScope.coroutineContext) {
        getSortModeUseCase().first()
    }

    fun saveCacheDeleteMode(value: Boolean) = viewModelScope.launch {
        saveCacheDeleteModeUseCase(value)
    }

    suspend fun getCacheDeleteMode() = withContext(viewModelScope.coroutineContext) {
        getCacheDeleteModeUseCase().first()
    }

    // WorkManager
    fun setWork() {
        val constraints = Constraints.Builder()
            // 충전 중 일때만
            .setRequiresCharging(true)
            // 배터리 잔량 충분
            .setRequiresBatteryNotLow(true)
            .build()

        // 15분에 한번식 work
        val workRequest = PeriodicWorkRequestBuilder<CacheDeleteWorker>(15, TimeUnit.MINUTES)
            // constraints 반영
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORKER_KEY, ExistingPeriodicWorkPolicy.REPLACE, workRequest
        )
    }

    fun deleteWork() = workManager.cancelUniqueWork(WORKER_KEY)

    // 현재 work 의 상태를 liveData type 으로 반환
    fun getWorkStatus(): LiveData<MutableList<WorkInfo>> =
        workManager.getWorkInfosForUniqueWorkLiveData(WORKER_KEY)

    companion object {
        // WorkManager 작업의 tag 로 사용
        private const val WORKER_KEY = "cache_worker"
    }
}
