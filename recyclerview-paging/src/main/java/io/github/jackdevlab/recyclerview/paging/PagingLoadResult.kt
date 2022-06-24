package io.github.jackdevlab.recyclerview.paging


sealed class PagingLoadResult {
    class Success(val dataList: MutableList<Any>) : PagingLoadResult()
    class End(val dataList: MutableList<Any>) : PagingLoadResult()
    class Error(val exception: Exception) : PagingLoadResult()
}