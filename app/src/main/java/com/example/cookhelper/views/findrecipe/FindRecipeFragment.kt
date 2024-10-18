package com.example.cookhelper.views.findrecipe

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.example.cookhelper.BuildConfig
import com.example.cookhelper.R
import com.example.cookhelper.databinding.FragmentFindRecipeBinding
import com.example.cookhelper.utils.getImageUri
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class FindRecipeFragment : Fragment() {

    private var _binding: FragmentFindRecipeBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri : Uri? = null
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnCamera.setOnClickListener { startCamera() }
            btnGallery.setOnClickListener { startGallery() }
            btnGenerate.setOnClickListener { generateRecipe() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun generateRecipe() {
        if (currentImageUri == null) {
            Toast.makeText(requireContext(), "Please add an Image", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                val response = generativeModel.generateContent(
                    content {
                        image(uriToBitmap())
                        text(requireContext().getString(R.string.promptText))
                    }
                )
                binding.progressBar.visibility = View.GONE
                binding.tvResult.text = response.text.toString()
                navigateToResultFragment(binding.root, currentImageUri!!, response.text.toString())
            }
        }

    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun uriToBitmap(): Bitmap {
        currentImageUri.let {
            val source = ImageDecoder.createSource(requireContext().contentResolver, it!!)
            return ImageDecoder.decodeBitmap(source)
        }
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(requireContext(), "No Picture Found", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage $it")
            binding.ivFoodMaterial.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun navigateToResultFragment(view: View, imageUri: Uri, text: String) {
        val action = FindRecipeFragmentDirections.actionFindRecipeFragmentToResultRecipeFragment(imageUri.toString(), text)
        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
        view.findNavController().navigate(action, options)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}