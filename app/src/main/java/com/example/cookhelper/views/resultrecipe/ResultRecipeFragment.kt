package com.example.cookhelper.views.resultrecipe

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookhelper.R

class ResultRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = ResultRecipeFragment()
    }

    private val viewModel: ResultRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_result_recipe, container, false)
    }
}