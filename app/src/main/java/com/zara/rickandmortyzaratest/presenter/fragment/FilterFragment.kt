package com.zara.rickandmortyzaratest.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zara.rickandmortyzaratest.R
import com.zara.rickandmortyzaratest.databinding.FilterFragmentBinding
import com.zara.rickandmortyzaratest.util.GenderFilter
import com.zara.rickandmortyzaratest.util.StatusFilter

/**
 * Fragment to apply Filters to the Character list
 */

class FilterFragment : BaseFragment() {

    private var _binding: FilterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRadioDataFromGender(mainViewModel.filterGender)
        setRadioDataFromStatus(mainViewModel.filterStatus)

        binding.apply {

            txvClear.setOnClickListener {
                mainViewModel.setGenderFilter(GenderFilter.NONE)
                mainViewModel.setStatusFilter(StatusFilter.NONE)
                binding.rgStatus.clearCheck()
                binding.rgGender.clearCheck()
            }

            rgStatus.setOnCheckedChangeListener { radioGroup, idThatSelected ->
                mainViewModel.setStatusFilter(
                    when (idThatSelected) {
                        binding.rbAlive.id -> StatusFilter.ALIVE
                        binding.rbDead.id -> StatusFilter.DEAD
                        binding.rbUnknown.id -> StatusFilter.UNKNOWN
                        else -> StatusFilter.NONE
                    }
                )
            }

            rgGender.setOnCheckedChangeListener { radioGroup, idThatSelected ->
                mainViewModel.setGenderFilter(
                    when (idThatSelected) {
                        binding.rbFemale.id -> GenderFilter.FEMALE
                        binding.rbMale.id -> GenderFilter.MALE
                        binding.rbgUnknown.id -> GenderFilter.UNKNOWN
                        binding.rbGenderless.id -> GenderFilter.GENDERLESS
                        else -> GenderFilter.NONE
                    }
                )
            }

            btnApply.setOnClickListener {
                mainViewModel.applyFilters()
                navController.navigate(R.id.action_filterFragment_to_characterListFragment)
            }
        }

    }


    private fun setRadioDataFromStatus(statusFilter: StatusFilter) {
        if (statusFilter == StatusFilter.NONE) {
            binding.rgStatus.clearCheck()
        } else {
            binding.rgStatus.check(
                when (statusFilter) {
                    StatusFilter.ALIVE -> binding.rbAlive.id
                    StatusFilter.DEAD -> binding.rbDead.id
                    else -> binding.rbUnknown.id
                }
            )
        }
    }


    private fun setRadioDataFromGender(genderFilter: GenderFilter) {
        if (genderFilter == GenderFilter.NONE) {
            binding.rgGender.clearCheck()
        } else {
            binding.rgGender.check(
                when (genderFilter) {
                    GenderFilter.MALE -> binding.rbMale.id
                    GenderFilter.FEMALE -> binding.rbFemale.id
                    GenderFilter.GENDERLESS -> binding.rbGenderless.id
                    else -> binding.rbgUnknown.id
                }
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}