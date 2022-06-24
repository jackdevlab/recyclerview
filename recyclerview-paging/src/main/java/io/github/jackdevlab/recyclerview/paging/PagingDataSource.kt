package io.github.jackdevlab.recyclerview.paging

import io.github.jackdevlab.recyclerview.DataSource
import io.github.jackdevlab.recyclerview.LoadResult
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.EmptyCoroutineContext

abstract class PagingDataSource(scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext), val prefetchDistance: Int = 2) : DataSource(scope) {

    override final suspend fun load(): LoadResult {
        val result = initialLoad()
        return when (result) {
            is PagingLoadResult.End -> LoadResult.Success(result.dataList)
            is PagingLoadResult.Error -> LoadResult.Error(result.exception)
            is PagingLoadResult.Success -> LoadResult.Success(result.dataList)
        }
    }

    abstract suspend fun initialLoad(): PagingLoadResult

    abstract suspend fun pagingLoad(): PagingLoadResult
}