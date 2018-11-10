package com.matheusvillela.hiperbolao.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.matheusvillela.hiperbolao.BolaoApp
import com.matheusvillela.hiperbolao.di.ViewModelFactory

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProviders.of(requireActivity(), ViewModelFactory.getInstance(BolaoApp.instance)).get(viewModelClass)