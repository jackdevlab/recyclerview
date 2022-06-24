package io.github.jackdevlab.recyclerview.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.jackdevlab.recyclerview.item
import io.github.jackdevlab.recyclerview.items
import io.github.jackdevlab.recyclerview.paging.PagingDataSource
import io.github.jackdevlab.recyclerview.paging.PagingLoadResult
import io.github.jackdevlab.recyclerview.paging.dataSource
import io.github.jackdevlab.recyclerview.refresh
import io.github.jackdevlab.recyclerview.sample.databinding.*
import kotlinx.coroutines.delay

class PagingRecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagingRecyclerViewBinding
    private val dataList = mutableListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagingRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.dataSource(object : PagingDataSource() {
            override suspend fun initialLoad(): PagingLoadResult {
                dataList.addAll(loadData())
                return PagingLoadResult.Success(dataList)
            }

            override suspend fun pagingLoad(): PagingLoadResult {
                dataList.addAll(loadData(2000L))
                return PagingLoadResult.Success(dataList)
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


    private suspend fun loadData(delay: Long=0L): MutableList<Any> {
        delay(delay)
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