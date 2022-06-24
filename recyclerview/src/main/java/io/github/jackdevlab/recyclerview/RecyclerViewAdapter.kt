package io.github.jackdevlab.recyclerview

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class RecyclerViewAdapter(private val dataSource: DataSource) : RecyclerView.Adapter<ViewBindingViewHolder<*, *>>() {

    protected val dataList = mutableListOf<Any>()
    protected val mergedList = mutableListOf<Any>()
    internal val viewHolderManager = ViewHolderManager()
    private val handler = Handler(Looper.getMainLooper())
    private var diffCallback: DiffCallback? = null
    protected var enablePlaceholders = false


    override fun getItemViewType(position: Int): Int {
        val data = mergedList.get(position)
        val viewType = viewHolderManager.getViewType(data)
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder<*, *> {
        return viewHolderManager.getGenerator(viewType)?.onCreateViewHolder(parent)
            ?: throw NullPointerException("创建ViewHolder失败,viewType:$viewType")
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<*, *>, position: Int) {
        holder.onBind(mergedList.get(position), position, mutableListOf())
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<*, *>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(mergedList.get(position), position, payloads)
        }
    }

    override fun getItemCount(): Int {
        return mergedList.size
    }

    fun setDiffCallback(diffCallback: DiffCallback?) {
        this.diffCallback = diffCallback
    }

    fun enablePlaceholders(enable: Boolean) {
        this.enablePlaceholders = enable
    }


    private fun mergeList(result: LoadResult.Success) {
        dataList.clear()
        dataList.addAll(result.dataList)
        mergedList.clear()
        dataSource.headers()?.let { mergedList.addAll(it) }
        mergedList.addAll(dataList)
        dataSource.footers()?.let { mergedList.addAll(it) }
    }


    final fun refresh() {
        dataSource.scope.launch(Dispatchers.IO) {
            if (enablePlaceholders) {
                val placeholdersList = mutableListOf<Any>().apply {
                    addAll(dataList)
                    dataSource.placeholders()?.let { addAll(it) }
                }
                notifyDataSetChangedCompat(LoadResult.Success(placeholdersList))
            }

            val result = dataSource.load()
            if (result is LoadResult.Success) {
                withContext(Dispatchers.Main) { notifyDataSetChangedCompat(result) }
            }
        }
    }

    protected fun notifyDataSetChangedCompat(result: LoadResult.Success) {
        if (diffCallback == null) {
            mergeList(result)
            handler.post { notifyDataSetChanged() }
        } else {
            notifyDataSetDiffChangedCompat(result)
        }
    }


    private final fun notifyDataSetDiffChangedCompat(result: LoadResult.Success) {
        val newList = mutableListOf<Any>()
        dataSource.headers()?.let { newList.addAll(it) }
        newList.addAll(result.dataList)
        dataSource.footers()?.let { newList.addAll(it) }
        val calculateResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return mergedList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = mergedList[oldItemPosition]
                val newItem = newList[newItemPosition]
                return diffCallback?.areItemsTheSame(oldItem, newItem) ?: false
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = mergedList[oldItemPosition]
                val newItem = newList[newItemPosition]
                return diffCallback?.areContentsTheSame(oldItem, newItem) ?: false
            }
        })
        mergeList(result)
        handler.post { calculateResult.dispatchUpdatesTo(this) }
    }


}