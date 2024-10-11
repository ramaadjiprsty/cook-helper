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

    private val viewModel: FindRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

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
                        text("Bahan apa saja yang tersedia di gambar ini?, dan juga berikan aku resep berdasarkan bahan-bahan dari gambar diatas dan cara membuatnya")
                    }
                )
                binding.progressBar.visibility = View.GONE
                binding.tvResult.text = response.text.toString()
                binding.btnGenerate.visibility = View.GONE
                dismissButton()
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

    private fun dismissButton() {
        binding.btnGenerate.visibility = View.GONE
        binding.btnGallery.visibility = View.GONE
        binding.btnCamera.visibility = View.GONE
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}