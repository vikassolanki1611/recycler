package com.recyclerapplication


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.recyclerapplication.response.SearchResponse
import com.recyclerapplication.webservice.ApiInterface
import com.recyclerapplication.webservice.NetworkCall
import com.recyclerapplication.webservice.NetworkState
import javax.inject.Inject

class Viewmodel @ViewModelInject constructor(private val mRepository: Repository) : ViewModel() {


    suspend fun getList(searchQuery:String,page:String): NetworkState<SearchResponse> {
        return mRepository.getlist(searchQuery,page)
    }


}
class Repository @Inject constructor(private val apiService: ApiInterface) : NetworkCall() {


    suspend fun getlist(searchQuery:String,page:String): NetworkState<SearchResponse> {
        return safeApiCall {

            apiService.getSearchData("b9bd48a6",searchQuery,"movie",page)
        }
    }


}