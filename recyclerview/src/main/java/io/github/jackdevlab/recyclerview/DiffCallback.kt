package io.github.jackdevlab.recyclerview

interface DiffCallback {

    fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean
    fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean
}