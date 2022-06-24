package io.github.jackdevlab.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ItemsBinder<DATA>(private val viewHolderManager: ViewHolderManager, private val clazz: Class<DATA>) {


    fun map(action: (data: DATA) -> Boolean): ItemsMapping<DATA> {
        return ItemsMapping(viewHolderManager, clazz, action)
    }


    class ItemsMapping<DATA>(private val viewHolderManager: ViewHolderManager, private val clazz: Class<DATA>, private val action: ((data: DATA) -> Boolean)) {

        @Suppress("UNCHECKED_CAST")
        fun <VIEWBINDING : ViewBinding> item(classOfViewBinding: Class<VIEWBINDING>, onBindViewHolderAction: ((binder: ViewHolderBinder<DATA, ViewBindingViewHolder<VIEWBINDING, DATA>>) -> Unit) = {}): ItemsBinder<DATA> {
            val generator = ViewHolderGenerator(clazz) { parent: ViewGroup ->
                val inflateMethod = classOfViewBinding.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                val binding = inflateMethod.invoke(null, LayoutInflater.from(parent.context), parent, false) as VIEWBINDING
                ViewBindingViewHolder<VIEWBINDING, DATA>(binding) { onBindViewHolderAction.invoke(it) }
            }
            viewHolderManager.addGenerator(clazz, action, generator)
            return ItemsBinder(viewHolderManager, clazz)
        }
    }
}