package io.github.jackdevlab.recyclerview.paging

import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.dataSource(dataSource: PagingDataSource) {
    adapter = PagingRecyclerViewAdapter(dataSource)
}

