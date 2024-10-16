package com.example.cookhelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cookhelper.R
import com.example.cookhelper.data.Recipe
import com.example.cookhelper.databinding.ItemListRecipeBinding
import com.example.cookhelper.views.getrecipe.GetRecipeFragmentDirections

class RecipeAdapter(private val recipe: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListRecipeBinding.bind(itemView)
        fun bind(recipe: Recipe) {
            binding.tvRecipe.text = recipe.name
            binding.ivRecipe.setImageResource(recipe.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = recipe[position]
        holder.apply {
            bind(data)
            itemView.setOnClickListener {
                val action = GetRecipeFragmentDirections.actionGetRecipeFragmentToDetailRecipeFragment(
                    imageRecipe = data.imageResId,
                    nameRecipe = data.name,
                    ingredients = data.ingredients,
                    instructions = data.instructions
                )
                findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipe.size
    }
}