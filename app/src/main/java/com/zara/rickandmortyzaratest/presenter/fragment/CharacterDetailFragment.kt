package com.zara.rickandmortyzaratest.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.zara.rickandmortyzaratest.databinding.CharacterDetailFragmentBinding


/**
 * Detail Fragment displaying the info of the selected Character
 */

class CharacterDetailFragment : BaseFragment() {

    private var _binding: CharacterDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CharacterDetailFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = character.name
            binding.apply {
                txvName.text = character.name
                txvGender.text = character.gender
                txvLocation.text = character.location.name
                txvOrigin.text = character.origin.name
                txvSpecies.text = character.species
                txvStatus.text = character.status
                txvType.text = character.type
                Glide.with(root)
                    .load(character.image)
                    .transform(CircleCrop())
                    .into(characterImage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}