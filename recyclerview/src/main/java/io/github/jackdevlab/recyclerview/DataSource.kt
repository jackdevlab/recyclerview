package io.github.jackdevlab.recyclerview

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import java.text.FieldPosition
import kotlin.coroutines.EmptyCoroutineContext

abstract class DataSource(val scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)) {

    open fun headers(): MutableList<Any>? {
        return null
    }

    open fun footers(): MutableList<Any>? {
        return null
    }

    open fun placeholders(): MutableList<Any>? {
        return null
    }

    abstract suspend fun load(): LoadResult

}