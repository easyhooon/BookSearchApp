package com.kenshi.presentation.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                // offer(text) is deprecated
                // 값 내보내기
                trySend(text)
            }
        }
        addTextChangedListener(listener)
        // 콜백이 사라질 때 실행, 리스너 제거
        awaitClose { removeTextChangedListener(listener) }
    }.onStart {
        // event 방출
        emit(text)
    }
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}