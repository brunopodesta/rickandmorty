package com.zara.rickandmortyzaratest.presenter.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zara.rickandmortyzaratest.R
import com.zara.rickandmortyzaratest.databinding.CharacterListFragmentBinding
import com.zara.rickandmortyzaratest.presenter.adapter.CharacterAdapter
import com.zara.rickandmortyzaratest.presenter.state.CharacterListState
import kotlinx.coroutines.flow.collectLatest

/**
 * Fragment to show Rick and Morty characters List
 */

class CharacterListFragment : BaseFragment() {

    private var binding: CharacterListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val characterListAdapter = CharacterAdapter() { id ->
            mainViewModel.onCharacterSelected(id)
            navController.navigate(R.id.action_characterListFragment_to_characterDetailFragment)
        }

        binding?.apply {

            charactersRV.apply {
                adapter = characterListAdapter
                layoutManager = LinearLayoutManager(context)
            }

            btnNext.setOnClickListener {
                mainViewModel.movePage(true)
            }

            btnPrevious.setOnClickListener {
                mainViewModel.movePage(false)
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collectLatest { state ->
                when (state) {
                    is CharacterListState.Success -> {
                        binding?.apply {
                            setComponentVisibility(
                                rvVisibility = View.VISIBLE,
                                errorTextVisibility = View.GONE,
                                progressVisibility = View.GONE
                            )
                            btnPrevious.isEnabled = state.showPrevious
                            btnNext.isEnabled = state.showNext
                        }
                        characterListAdapter.characterList = state.dataList
                    }
                    is CharacterListState.Error -> {
                        binding?.apply {
                            setComponentVisibility(
                                errorTextVisibility = View.VISIBLE,
                                progressVisibility = View.GONE,
                                rvVisibility = View.GONE
                            )
                            tvErrorMessage.text = state.error
                        }
                    }
                    is CharacterListState.Loading -> {
                        binding?.apply {
                            setComponentVisibility(
                                progressVisibility = View.VISIBLE,
                                errorTextVisibility = View.GONE,
                                rvVisibility = View.GONE
                            )
                        }
                    }
                }
            }
        }

        setMenu()
    }

    /**
     * Function to Show or Hide components according to the State
     */

    private fun setComponentVisibility(
        rvVisibility: Int,
        errorTextVisibility: Int,
        progressVisibility: Int
    ) {
        binding?.apply {
            pgLoading.visibility = progressVisibility
            tvErrorMessage.visibility = errorTextVisibility
            charactersRV.visibility = rvVisibility
        }
    }

    /**
     * Set Filter menu to access to Filter Screen
     */

    private fun setMenu() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menuFilter -> {
                        navController.navigate(R.id.action_characterListFragment_to_filterFragment)
                        true
                    }
                    else -> false
                }
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }

        }, viewLifecycleOwner, Lifecycle.State.CREATED)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

enum class UIState() {
    SUCCESS(),
    ERROR(),
    LOADING()
}