package com.guuguo.android.pikacomic.app.viewModel

import android.databinding.BaseObservable
import com.guuguo.android.pikacomic.app.fragment.ComicsFragment


/**
 * mimi 创造于 2017-05-22.
 * 项目 pika
 */
class ComicsViewModel(val fragment: ComicsFragment) : BaseObservable() {
    val activity = fragment.activity
  
}