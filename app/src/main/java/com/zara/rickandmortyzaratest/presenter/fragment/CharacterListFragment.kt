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

    private var _binding : CharacterListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val characterListAdapter = CharacterAdapter(){ id ->
            mainViewModel.onCharacterSelected(id)
            navController.navigate(R.id.action_characterListFragment_to_characterDetailFragment)
        }

        binding.charactersRV.apply {
            adapter = characterListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.apply {
            btnNext.setOnClickListener {
                mainViewModel.movePage(true)
            }

            btnPrevious.setOnClickListener {
                mainViewModel.movePage(false)
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collectLatest { state ->

                when(state){
                    is CharacterListState.Success -> {
                        binding.apply {
                            charactersRV.visibility = View.VISIBLE
                            btnPrevious.isEnabled = state.showPrevious
                            btnNext.isEnabled = state.showNext
                            pgLoading.visibility = View.GONE
                            tvErrorMessage.visibility = View.GONE
                        }
                        characterListAdapter.characterList = state.dataList
                    }
                    is CharacterListState.Error ->{
                        binding.apply {
                            tvErrorMessage.visibility = View.VISIBLE
                            tvErrorMessage.text = state.error
                            charactersRV.visibility = View.GONE
                            pgLoading.visibility = View.GONE
                        }
                    }
                    is CharacterListState.Loading ->{
                        binding.apply {
                            pgLoading.visibility = View.VISIBLE
                            tvErrorMessage.visibility = View.GONE
                            charactersRV.visibility = View.GONE
                        }
                    }

                }
            }
        }

        setMenu()
    }

    fun setMenu() {

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
        _binding = null
    }

}