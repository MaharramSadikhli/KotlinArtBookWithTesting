package com.imsosoft.kotlinartbookwithtesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imsosoft.kotlinartbookwithtesting.model.PixAbayResponse
import com.imsosoft.kotlinartbookwithtesting.repo.ArtRepositoryInterface
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
import com.imsosoft.kotlinartbookwithtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
): ViewModel() {

    val artList = repository.getArt()

    private val images = MutableLiveData<Resource<PixAbayResponse>>()
    val imageList: LiveData<Resource<PixAbayResponse>>
        get() = images


    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage



    private var insertMsg = MutableLiveData<Resource<ArtEntity>>()
    val insertMessage: LiveData<Resource<ArtEntity>>
        get() = insertMsg



    // for solve the navigation bug
    fun resetInsertMsg() {
        insertMsg = MutableLiveData<Resource<ArtEntity>>()
    }

    fun setSelectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: ArtEntity) = viewModelScope.launch {
        repository.deleteArt(art)
    }

    fun insertArt(art: ArtEntity) = viewModelScope.launch {
        repository.insertArt(art)
    }


    fun searchForImage (searchString: String) {

        if (searchString.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }

    }

    fun makeArt (name: String, artistName: String, year: String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()) {
            insertMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }

        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertMsg.postValue(Resource.error("Year should be number", null))
            return
        }

        val art = ArtEntity(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertMsg.postValue(Resource.success(art))
    }


}