package com.example.caloric

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.caloric.databinding.ActivityMainBinding
import com.example.caloric.databinding.IngredientItemBinding
import com.example.caloric.databinding.IngredientItemBinding.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecipeDropdown()
    }

    private fun setupRecipeDropdown() {
        val recipes = listOf("Cake", "Sandwich", "Cookies", "Apple Pie")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, recipes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.recipeDropdown.adapter = adapter
        binding.recipeDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRecipe = recipes[position]
                displayIngredients(selectedRecipe)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun displayIngredients(recipe: String) {
        val ingredients = when (recipe) {
            "Cake" -> listOf("Flour", "Sugar", "Butter", "Eggs")
            "Sandwich" -> listOf("Bread", "Cheese", "Lettuce", "Tomato")
            "Cookies" -> listOf("Flour", "Sugar", "Butter", "Chocolate Chips")
            "Apple Pie" -> listOf("Apples", "Sugar", "Flour", "Butter")
            else -> emptyList()
        }

        binding.ingredientsContainer.removeAllViews()
        ingredients.forEach { ingredient ->
            val itemView = layoutInflater.inflate(R.layout.ingredient_item, binding.ingredientsContainer, false)
            val itemBinding = bind(itemView)
            itemBinding.ingredientName.text = ingredient
            itemBinding.ingredientSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    itemBinding.calorieValue.text = "${progress * 10} cal"
                    calculateTotalCalories()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            binding.ingredientsContainer.addView(itemView)
        }
    }

    private fun calculateTotalCalories() {
        var totalCalories = 0
        for (i in 0 until binding.ingredientsContainer.childCount) {
            val itemView = binding.ingredientsContainer.getChildAt(i)
            val itemBinding = bind(itemView)
            val calories = itemBinding.ingredientSlider.progress * 10
            totalCalories += calories
        }
        binding.totalCalories.text = "Total Calories: $totalCalories cal"
    }
}
