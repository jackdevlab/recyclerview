package io.github.jackdevlab.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewHolderGenerator<DATA, VIEWBINDING : ViewBinding>(private val clazz: Class<DATA>, private val onCreateViewHolderAction: ((parent: ViewGroup) -> ViewBindingViewHolder<VIEWBINDING, DATA>)) {

    fun onCreateViewHolder(parent: ViewGroup): ViewBindingViewHolder<VIEWBINDING, DATA> {
        return onCreateViewHolderAction.invoke(parent)
    }

}