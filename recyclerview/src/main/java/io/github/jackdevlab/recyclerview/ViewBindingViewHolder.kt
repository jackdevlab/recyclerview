package io.github.jackdevlab.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewBindingViewHolder<VIEWBINDING : ViewBinding, DATA>(val binding: VIEWBINDING, private val onBindViewHolderAction: ((binder: ViewHolderBinder<DATA, ViewBindingViewHolder<VIEWBINDING, DATA>>) -> Unit)) : RecyclerView.ViewHolder(binding.root) {

    @Suppress("UNCHECKED_CAST")
    internal fun onBind(data: Any, position: Int, payloads: MutableList<Any>) {
        onBindViewHolderAction.invoke(ViewHolderBinder(data as DATA, this, position, payloads))
    }

}
