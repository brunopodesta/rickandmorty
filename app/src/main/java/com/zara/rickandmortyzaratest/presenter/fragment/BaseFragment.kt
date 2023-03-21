package com.zara.rickandmortyzaratest.presenter.fragment

import androidx.fragment.app.Fragment
import com.zara.rickandmortyzaratest.presenter.view.MainActivity
import com.zara.rickandmortyzaratest.presenter.viewmodel.RickAndMortyViewModel

abstract class BaseFragment : Fragment() {

    protected val mainViewModel : RickAndMortyViewModel
    get() = (activity as MainActivity).mainViewModel

    protected val navController by lazy {
        (activity as MainActivity).navController
    }
}