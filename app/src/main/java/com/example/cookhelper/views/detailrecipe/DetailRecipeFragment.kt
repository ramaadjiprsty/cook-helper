package com.example.cookhelper.views.detailrecipe

import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cookhelper.R
import com.example.cookhelper.R.layout.fragment_detail_recipe
import com.example.cookhelper.data.Recipe
import com.example.cookhelper.databinding.FragmentDetailRecipeBinding
import com.google.android.material.appbar.MaterialToolbar

class DetailRecipeFragment : Fragment() {

    private var _binding: FragmentDetailRecipeBinding? = null
    private val binding get() = _binding!!

    private val args: DetailRecipeFragmentArgs by navArgs()

    private val viewModel: DetailRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivRecipeDetail.setImageResource(args.imageRecipe)
            tvRecipeName.text = args.nameRecipe
            tvRecipeIngredients.text = args.ingredients
            tvRecipeInstructions.text = args.instructions
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}