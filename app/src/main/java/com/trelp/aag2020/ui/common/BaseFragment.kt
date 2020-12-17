package com.trelp.aag2020.ui.common

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    //    Backing property
    protected var viewBinding: ViewBinding? = null

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}