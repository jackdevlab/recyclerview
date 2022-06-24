package io.github.jackdevlab.recyclerview

sealed class LoadResult {
    class Success(val dataList: MutableList<Any>) : LoadResult()
    class Error(val exception: Exception) : LoadResult()
}
