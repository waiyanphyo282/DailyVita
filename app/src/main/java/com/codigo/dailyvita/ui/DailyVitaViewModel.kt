package com.codigo.dailyvita.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codigo.dailyvita.data.Allergies
import com.codigo.dailyvita.data.Allergy
import com.codigo.dailyvita.data.Diet
import com.codigo.dailyvita.data.DietList
import com.codigo.dailyvita.data.HealthConcern
import com.codigo.dailyvita.data.HealthConcernList
import com.codigo.dailyvita.data.toHealthConcernList
import com.codigo.dailyvita.utils.TAG
import com.codigo.dailyvita.utils.readJSONFromAssets
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import java.util.Collections
import javax.inject.Inject


@HiltViewModel
class DailyVitaViewModel @Inject constructor(): ViewModel() {

    private val _chosenHealthConcerns = MutableLiveData<List<HealthConcern>>(mutableListOf())
    val chosenHealthConcernsLiveData: LiveData<List<HealthConcern>> get() = _chosenHealthConcerns

    private val _diets = MutableLiveData<List<Diet>>(mutableListOf())

    val checkedDiets: List<Diet>? get() = _diets.value

    private val _allergies = MutableLiveData<List<Allergy>>(mutableListOf())

    val allergiesLiveData: LiveData<List<Allergy>> get() = _allergies


    fun swapHealthConcerns(fromPosition: Int, toPosition: Int) {
        Collections.swap(_chosenHealthConcerns.value, fromPosition, toPosition)
    }

    fun toggleCheckedConcerns(concern: HealthConcern) {
        _chosenHealthConcerns.value?.toMutableList()?.let { concerns ->
            if (concerns.find { it.id == concern.id } != null) {
                concerns.remove(concern)
            } else concerns.add(concern)
            _chosenHealthConcerns.postValue(concerns)
        }
    }

    fun toggleCheckedDiets(diet: Diet) {
        _diets.value?.toMutableList()?.let { diets ->
            if (diets.find { it.id == diet.id } != null) {
                diets.remove(diet)
            } else diets.add(diet)
            _diets.postValue(diets)
        }
    }

    fun toggleAllergies(allergy: Allergy) {
        _allergies.value?.toMutableList()?.let {allergies ->
            if (allergies.find { it.id == allergy.id } != null) {
                allergies.remove(allergy)
            } else allergies.add(allergy)
            _allergies.postValue(allergies)
        }
    }

    fun getHealthConcerns(context: Context): List<HealthConcern> {
        val concernList = mutableListOf<HealthConcern>()
        val jsonString = readJSONFromAssets(context, "Healthconcern.json")
        Gson().fromJson(jsonString, HealthConcernList::class.java).data.map {
            if (_chosenHealthConcerns.value?.contains(it) == true) {
                concernList.add(it.copy(checked = true))
            } else {
                concernList.add(it)
            }
        }
        return concernList
    }

    fun getDiets(context: Context): List<Diet> {
        val jsonString = readJSONFromAssets(context, "Diets.json")
        val dietList = mutableListOf<Diet>()
        Gson().fromJson(jsonString, DietList::class.java).data.map {
            if (_diets.value?.contains(it) == true) {
                Log.d(TAG, "getDiets: checked Item $it")
                dietList.add(it.copy(checked = true))
            } else {
                Log.d(TAG, "getDiets: unchecked Item $it")
                dietList.add(it)
            }
        }
        return dietList
    }

    fun getAllergies(context: Context): List<Allergy> {
        val jsonString = readJSONFromAssets(context, "allergies.json")
        return Gson().fromJson(jsonString, Allergies::class.java).data
    }

    fun getVitamin(smoke: Boolean, dailyExposure: Boolean, alcohol: String) {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        val json = JSONObject()
        with(json) {
            put("health_concerns", gson.toJson(_chosenHealthConcerns.value))
            put("diets", gson.toJson(_diets.value))
            put("allergies", gson.toJson(_allergies.value))
            put("is_smoke", smoke)
            put("is_daily_exposure", dailyExposure)
            put("alcohol", alcohol)
        }
        println(json)
        Log.d(TAG, "getVitamin: JSON $json")
    }


}