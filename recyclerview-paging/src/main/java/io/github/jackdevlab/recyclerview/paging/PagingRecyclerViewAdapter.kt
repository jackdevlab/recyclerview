package io.github.jackdevlab.recyclerview.paging

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.jackdevlab.recyclerview.*
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class PagingRecyclerViewAdapter(private val dataSource: PagingDataSource) : RecyclerViewAdapter(dataSource) {

    private var pageIndex = 0
    private var isPageLoading = false
    private var isPageEnd = false


    override fun onBindViewHolder(holder: ViewBindingViewHolder<*, *>, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<*, *>, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        Log.e("PagingRecyclerViewAdapter", "position:${position}")
        if (isPageLoading) {
            return
        }
        if (isPageEnd) {
            return
        }
        val distance = if (enablePlaceholders) {
            dataList.size - position - 1 - (dataSource.placeholders()?.size ?: 0)
        } else {
            dataList.size - position - 1
        }
        Log.e("PagingRecyclerViewAdapter", "distance:${distance}")
        if (distance > dataSource.prefetchDistance) {
            return
        }
        isPageLoading = true
        Log.e("PagingRecyclerViewAdapter", "isPageLoading:${isPageLoading}")
        dataSource.scope.launch(Dispatchers.IO) {
            val result = dataSource.pagingLoad()
            when (result) {
                is PagingLoadResult.End -> {
                    isPageEnd = true
                    withContext(Dispatchers.Main) { notifyDataSetChangedCompat(LoadResult.Success(result.dataList)) }
                }
                is PagingLoadResult.Error -> {

                }
                is PagingLoadResult.Success -> {
                    if (enablePlaceholders) {
                        val placeholdersList = mutableListOf<Any>().apply {
                            addAll(result.dataList)
                            dataSource.placeholders()?.let { addAll(it) }
                        }
                        withContext(Dispatchers.Main) { notifyDataSetChangedCompat(LoadResult.Success(placeholdersList)) }
                    } else {
                        withContext(Dispatchers.Main) { notifyDataSetChangedCompat(LoadResult.Success(result.dataList)) }
                    }
                }
            }
            isPageLoading = false
        }
    }

}