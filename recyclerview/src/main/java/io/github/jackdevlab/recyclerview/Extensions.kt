package io.github.jackdevlab.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


fun RecyclerView.dataSource(dataSource: DataSource) {
    adapter = RecyclerViewAdapter(dataSource)
}

fun RecyclerView.refresh() {
    (adapter as RecyclerViewAdapter).refresh()
}

fun RecyclerView.diffCallback(diffCallback: DiffCallback?) {
    (adapter as RecyclerViewAdapter).setDiffCallback(diffCallback)
}

fun RecyclerView.enablePlaceholders(enable: Boolean) {
    (adapter as RecyclerViewAdapter).enablePlaceholders(enable)
}


fun <DATA, VIEWBINDING : ViewBinding> RecyclerView.item(clazz: Class<DATA>, classOfViewBinding: Class<VIEWBINDING>, onBindViewHolderAction: ((binder: ViewHolderBinder<DATA, ViewBindingViewHolder<VIEWBINDING, DATA>>) -> Unit) = {}) {
    val recyclerViewAdapter = adapter as RecyclerViewAdapter
    ItemsBinder<DATA>(recyclerViewAdapter.viewHolderManager, clazz)
        .map { true }
        .item(classOfViewBinding, onBindViewHolderAction)
}

fun <DATA> RecyclerView.items(clazz: Class<DATA>): ItemsBinder<DATA> {
    val recyclerViewAdapter = adapter as RecyclerViewAdapter
    return ItemsBinder<DATA>(recyclerViewAdapter.viewHolderManager, clazz)
}