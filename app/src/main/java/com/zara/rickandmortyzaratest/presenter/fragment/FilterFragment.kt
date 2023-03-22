package com.zara.rickandmortyzaratest.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zara.rickandmortyzaratest.databinding.FilterFragmentBinding
import com.zara.rickandmortyzaratest.util.GenderFilter
import com.zara.rickandmortyzaratest.util.StatusFilter

/**
 * Fragment to apply Filters to the Character list
 */

class FilterFragment : BaseFragment() {

    private var binding: FilterFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRadioDataFromGender(mainViewModel.filterGender)
        setRadioDataFromStatus(mainViewModel.filterStatus)

        binding?.apply {

            txvClear.setOnClickListener {
                mainViewModel.setGenderFilter(GenderFilter.NONE)
                mainViewModel.setStatusFilter(StatusFilter.NONE)
                rgStatus.clearCheck()
                rgGender.clearCheck()
            }

            rgStatus.setOnCheckedChangeListener { radioGroup, idThatSelected ->
                binding?.let {
                    mainViewModel.setStatusFilter(
                        when (idThatSelected) {
                            rbAlive.id -> StatusFilter.ALIVE
                            rbDead.id -> StatusFilter.DEAD
                            rbUnknown.id -> StatusFilter.UNKNOWN
                            else -> StatusFilter.NONE
                        }
                    )
                }
            }

            rgGender.setOnCheckedChangeListener { radioGroup, idThatSelected ->
                binding?.let {
                    mainViewModel.setGenderFilter(
                        when (idThatSelected) {
                            rbFemale.id -> GenderFilter.FEMALE
                            rbMale.id -> GenderFilter.MALE
                            rbgUnknown.id -> GenderFilter.UNKNOWN
                            rbGenderless.id -> GenderFilter.GENDERLESS
                            else -> GenderFilter.NONE
                        }
                    )
                }
            }

            btnApply.setOnClickListener {
                mainViewModel.applyFilters()
                navController.popBackStack()
            }
        }

    }


    private fun setRadioDataFromStatus(statusFilter: StatusFilter) {
        if (statusFilter == StatusFilter.NONE) {
            binding?.rgStatus?.clearCheck()
        } else {
            when (statusFilter) {
                StatusFilter.ALIVE -> binding?.rbAlive?.id
                StatusFilter.DEAD -> binding?.rbDead?.id
                else -> binding?.rbUnknown?.id
            }?.let {
                binding?.rgStatus?.check(
                    it
                )
            }
        }
    }


    private fun setRadioDataFromGender(genderFilter: GenderFilter) {
        if (genderFilter == GenderFilter.NONE) {
            binding?.rgGender?.clearCheck()
        } else {
            when (genderFilter) {
                GenderFilter.MALE -> binding?.rbMale?.id
                GenderFilter.FEMALE -> binding?.rbFemale?.id
                GenderFilter.GENDERLESS -> binding?.rbGenderless?.id
                else -> binding?.rbgUnknown?.id
            }?.let {
                binding?.rgGender?.check(
                    it
                )
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}