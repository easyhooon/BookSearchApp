package com.kenshi.booksearchapp.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(@LayoutRes val layoutId: Int) : AppCompatActivity() {

    protected lateinit var binding: VB

    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        preload()
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

        init()
    }

    abstract fun preload()

    abstract fun init()
}
