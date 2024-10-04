package com.belia.cookit.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belia.cookit.api.MealAPIService
import com.belia.cookit.models.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealViewModel : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals = _meals.asStateFlow()

    private val _hasError = MutableStateFlow(false)
    val hasError = _hasError.asStateFlow()

    fun getMeals(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _meals.update {
                    MealAPIService.callable.getMeals(category).meals
                }
                _hasError.update { false }
            } catch (e: Exception) {
                Log.d("trace", e.message.toString())
                _hasError.update { true }
            }
        }
    }
}