package io.github.jackdevlab.recyclerview.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.jackdevlab.recyclerview.*
import io.github.jackdevlab.recyclerview.sample.databinding.*
import kotlinx.coroutines.delay

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding
    private val dataList = mutableListOf<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.dataSource(object : DataSource() {
            override suspend fun load(): LoadResult {
                dataList.addAll(loadData())
                return LoadResult.Success(dataList)
            }
        })
        binding.recyclerview.item(SampleModel1::class.java, ItemSample1Binding::class.java) {
            it.holder.binding.tvItem.text = "data:${it.javaClass.name} text:${it.data.text}"
        }
        binding.recyclerview.items(SampleModel2::class.java)
            .map { it.type == 1 }
            .item(ItemSample2Type1Binding::class.java) {
                it.holder.binding.tvItem.text = "data:${it.javaClass.name} type:${it.data.type}"
            }
            .map { it.type == 2 }
            .item(ItemSample2Type2Binding::class.java) {
                it.holder.binding.tvItem.text = "data:${it.javaClass.name} type:${it.data.type}"
            }
            .map { it.type == 3 }
            .item(ItemSample2Type3Binding::class.java) {
                it.holder.binding.tvItem.text = "data:${it.javaClass.name} type:${it.data.type}"
            }
        binding.recyclerview.refresh()
    }

    private suspend fun loadData(): MutableList<Any> {
        return mutableListOf(
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1(),
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1(),
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1(),
            SampleModel1(),
            SampleModel2(1),
            SampleModel2(2),
            SampleModel2(3),
            SampleModel2(2),
            SampleModel1()
        )
    }
}