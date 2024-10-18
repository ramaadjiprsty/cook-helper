package com.example.cookhelper.views.resultrecipe

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookhelper.R
import com.example.cookhelper.databinding.FragmentGetRecipeBinding
import com.example.cookhelper.databinding.FragmentResultRecipeBinding

class ResultRecipeFragment : Fragment() {

    private var _binding: FragmentResultRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ResultRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUri = arguments?.getString("image")
        val resultText = arguments?.getString("resultText")

        binding.tvResult.text = resultText?.replace("**", "")
        binding.ivFoodMaterial.setImageURI(Uri.parse(imageUri))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}