package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.R
import com.example.domain.GetBreedsUsecase

internal class PupzListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val breedsRepository = (application as PupzApplication).provideRepository()
        val getBreedsUsecase = GetBreedsUsecase(breedsRepository)
        // TODO: use ViewModelProviders (or whatever is not deprecated now)
        PupzListViewModel(getBreedsUsecase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pupz_list)
        viewModel.breeds.observe(this, Observer { t ->
            if (t == null) return@Observer

        })
    }
}
