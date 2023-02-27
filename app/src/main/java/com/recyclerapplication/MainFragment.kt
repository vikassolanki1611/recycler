package com.recyclerapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerapplication.databinding.MainFragmentBinding
import com.recyclerapplication.response.Search
import com.recyclerapplication.webservice.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mViewModel: Viewmodel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private var searchList:ArrayList<Search> = ArrayList()
    var search=""
    var paging=0
    companion object {
        fun getInstance()= MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchListener()
        recyclerListener()
    }

    fun searchListener(){
        binding.searchInput.setQueryHint("Search View")
        binding.searchInput.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                search=query
                paging=1
                getSearchList(search,paging.toString(),true)
                return true
            }

        })


    }


    fun recyclerListener(){
        binding.listRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                try {
                        val myLayoutManager: GridLayoutManager =
                            binding.listRv?.layoutManager as GridLayoutManager
                        val scrollPosition = myLayoutManager.findLastVisibleItemPosition()
                        if (scrollPosition == searchList.size - 1) {
                            paging += 1
                        getSearchList(search,paging.toString(),false)

                        }

                } catch (e: Exception) {

                }

            }
        })
    }



    fun initRecycler(coupons:ArrayList<Search>){
        var listadapter = ListAdapter(coupons, requireContext())
      //  binding.listRv.smoothScrollToPosition(0)
        binding.listRv.adapter = listadapter
    }

    fun getSearchList(Search:String,Paging:String ,clearArray:Boolean)
    {
        lifecycleScope.launch {
            val response=mViewModel.getList(Search,Paging)
            when(response)
            {is NetworkState.Success -> {
                   if (response.body.response=="True"){
                       binding.noData.visibility=View.GONE
                       binding.listRv.visibility=View.VISIBLE
                       if (clearArray){
                           searchList.clear()
                           searchList=response.body.search
                           initRecycler(searchList)
                       }
                       else{
                           response.body.search.forEach {
                               searchList.add(it)
                           }
                           initRecycler(searchList)
                       }
                   }
                else{
                    binding.noData.visibility=View.VISIBLE
                       binding.listRv.visibility=View.GONE
                }
                }
                is NetworkState.Error<*> -> {
                     Toast.makeText(context, response.msg.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkState.NetworkException -> {

                    Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
                }
                is NetworkState.HttpErrors.InternalServerError -> {
                    Toast.makeText(context, "Internal server Error", Toast.LENGTH_SHORT).show()
                }
                is NetworkState.HttpErrors.ResourceNotFound -> {
                    Toast.makeText(context, "404 Not Found", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
