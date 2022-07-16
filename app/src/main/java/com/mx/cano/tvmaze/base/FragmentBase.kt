package com.mx.cano.tvmaze.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mx.cano.tvmaze.MainActivity

abstract class FragmentBase<T: ViewBinding>(@LayoutRes private var layoutInt: Int): Fragment() {

    private val activity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutInt, container, false)
        _binding = getBinding(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    abstract fun getBinding(view: View): T

    abstract fun setupFragment()

}