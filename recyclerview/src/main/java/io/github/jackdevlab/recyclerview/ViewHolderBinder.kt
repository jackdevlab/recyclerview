package io.github.jackdevlab.recyclerview

import androidx.recyclerview.widget.RecyclerView

class ViewHolderBinder<DATA, VIEWHOLDER : RecyclerView.ViewHolder>(
    val data: DATA,
    val holder: VIEWHOLDER,
    val position: Int,
    val payloads: MutableList<Any>
)