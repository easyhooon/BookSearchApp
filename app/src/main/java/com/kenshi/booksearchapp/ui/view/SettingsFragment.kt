package com.kenshi.booksearchapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kenshi.booksearchapp.R
import com.kenshi.booksearchapp.databinding.FragmentSettingsBinding
import com.kenshi.booksearchapp.ui.base.BaseFragment
import com.kenshi.booksearchapp.ui.viewmodel.SettingsViewModel
import com.kenshi.booksearchapp.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {

    //private lateinit var bookSearchViewModel: BookSearchViewModel
    //private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun getViewBinding() = FragmentSettingsBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSettings()
        loadSettings()
        showWorkStatus()
    }

    private fun saveSettings() = with(binding) {
        rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            settingsViewModel.saveSortMode(value)
        }

        swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveCacheDeleteMode(isChecked)
            if (isChecked) {
                settingsViewModel.setWork()
            } else {
                settingsViewModel.deleteWork()
            }
        }
    }

    private fun loadSettings() = with(binding) {
        lifecycleScope.launch {
            val buttonId = when (settingsViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            rgSort.check(buttonId)
        }

        lifecycleScope.launch {
            val mode = settingsViewModel.getCacheDeleteMode()
            //cache 버튼의 활성화 여부 반영
            swCacheDelete.isChecked = mode
        }
    }

    private fun showWorkStatus() = with(binding) {
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManager", workInfo.toString())
            // 초기엔 값이 존재하지 않기 때문에 분기처리
            if (workInfo.isEmpty()) {
                tvWorkStatus.text = getString(R.string.no_works)
            } else {
                // 현재 work 상태 가져오기
                tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }
}