package io.github.jackdevlab.recyclerview.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.jackdevlab.recyclerview.DataSource
import io.github.jackdevlab.recyclerview.LoadResult
import io.github.jackdevlab.recyclerview.dataSource
import io.github.jackdevlab.recyclerview.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRecyclerView.setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }

        binding.btnPagingRecyclerView.setOnClickListener {
            startActivity(Intent(this, PagingRecyclerViewActivity::class.java))
        }
    }

}