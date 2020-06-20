package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.R
import com.example.domain.GetBreedsUsecase
import kotlinx.android.synthetic.main.activity_pupz_list.*

internal class PupzListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val breedsRepository = (application as PupzApplication).provideRepository()
        val getBreedsUsecase = GetBreedsUsecase(breedsRepository)
        // TODO: use ViewModelProviders (or whatever is not deprecated now)
        PupzListViewModel(getBreedsUsecase)
    }

    private val pupzListAdapter = PupzListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pupz_list)
        recyclerView.adapter = pupzListAdapter
        viewModel.breeds.observe(this, Observer { t ->
            if (t == null) return@Observer
            pupzListAdapter.submitList(t)
        })
    }
}
