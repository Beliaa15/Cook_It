package com.belia.cookit.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belia.cookit.api.MealAPIService
import com.belia.cookit.models.Category
import com.belia.cookit.models.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals = _meals.asStateFlow()

    private val _hasError = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _categories.update {
                    MealAPIService.callable.getCategories().categories
                }
                _hasError.update { false }
            } catch (e: Exception) {
                _hasError.update { true }
                Log.d("trace", "Error: ${e.message}")
            }
        }

    }

    fun getMeals(categoryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _meals.update {
                    MealAPIService.callable.getMeals(categoryName).meals
                }
                _hasError.update { false }
            } catch (e: Exception) {
                _hasError.update { true }
                Log.d("trace", "Error: ${e.message}")
            }
        }
    }

}