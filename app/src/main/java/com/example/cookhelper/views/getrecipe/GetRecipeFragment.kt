package com.example.cookhelper.views.getrecipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cookhelper.R
import com.example.cookhelper.adapter.RecipeAdapter
import com.example.cookhelper.data.RecipeData
import com.example.cookhelper.databinding.FragmentGetRecipeBinding

class GetRecipeFragment : Fragment() {

    private var _binding: FragmentGetRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GetRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvRecipe.adapter = RecipeAdapter(RecipeData.recipes)
        binding.rvRecipe.setHasFixedSize(true)
        binding.rvRecipe.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}