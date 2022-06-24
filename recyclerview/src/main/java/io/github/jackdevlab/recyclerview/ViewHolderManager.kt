package io.github.jackdevlab.recyclerview

class ViewHolderManager {

    private val generatorMap = mutableMapOf<Int, ViewHolderGenerator<*, *>>()
    private val viewTypeMap = mutableMapOf<String, ViewTypeMapping>()
    private var viewType = 1000

    fun <DATA> addGenerator(clazz: Class<DATA>, viewTypeMappingAction: ((data: DATA) -> Boolean), generator: ViewHolderGenerator<*, *>) {
        val viewTypeMapping = viewTypeMap[clazz.name] ?: ViewTypeMapping()
        viewType = viewType + 1
        generatorMap[viewType] = generator
        viewTypeMapping.addAction(viewType, object : ViewTypeMappingAction<DATA>() {
            override fun mactch(data: DATA): Boolean {
                return viewTypeMappingAction.invoke(data)
            }
        })
        viewTypeMap[clazz.name] = viewTypeMapping
    }

    fun getGenerator(viewType: Int): ViewHolderGenerator<*, *>? {
        return generatorMap[viewType]
    }

    fun getViewType(data: Any): Int {
        return viewTypeMap[data::class.java.name]?.getViewType(data)
            ?: throw NullPointerException("ViewHolderManager getViewType失败,data:$data")
    }


    class ViewTypeMapping() {

        private val actionMap = mutableMapOf<Int, ViewTypeMappingAction<*>>()

        fun addAction(viewType: Int, action: ViewTypeMappingAction<*>) {
            actionMap[viewType] = action
        }

        fun getViewType(data: Any): Int {
            return actionMap.entries.first { it.value.mactchCast(data) }.key
        }

    }

    abstract class ViewTypeMappingAction<DATA> {
        abstract fun mactch(data: DATA): Boolean

        @Suppress("UNCHECKED_CAST")
        final fun mactchCast(data: Any): Boolean {
            return mactch(data as DATA)
        }
    }

}