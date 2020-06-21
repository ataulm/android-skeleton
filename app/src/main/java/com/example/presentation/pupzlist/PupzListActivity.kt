package com.example.presentation.pupzlist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.R
import com.example.domain.GetBreedsUsecase
import com.example.presentation.EventObserver
import com.example.presentation.NonNullObserver
import com.example.presentation.PupzApplication
import com.example.presentation.images.ImagesActivity
import kotlinx.android.synthetic.main.activity_pupz_list.*

internal class PupzListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val breedsRepository = (application as PupzApplication).provideRepository()
        val getBreedsUsecase = GetBreedsUsecase(breedsRepository)
        @Suppress("UNCHECKED_CAST") val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PupzListViewModel(getBreedsUsecase) as T
            }
        }
        ViewModelProvider(this, factory).get(PupzListViewModel::class.java)
    }

    private val pupzListAdapter = PupzListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pupz_list)
        recyclerView.adapter = pupzListAdapter
        viewModel.state.observe(this, NonNullObserver { state ->
            when (state) {
                is PupzListUiModel.Data -> {
                    pupzListAdapter.submitList(state.items)
                    loadingErrorView.visibility = View.GONE
                    feelingLuckyButton.setOnClickListener { state.onClickFeelingLucky.handler(Unit) }
                    feelingLuckyButton.show()
                }
                is PupzListUiModel.Error -> {
                    loadingErrorView.visibility = View.VISIBLE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = state.message
                    feelingLuckyButton.hide()
                }
                PupzListUiModel.Loading -> {
                    loadingErrorView.visibility = View.VISIBLE
                    errorTextView.visibility = View.INVISIBLE
                    feelingLuckyButton.hide()
                }
            }
        })

        viewModel.events.observe(this, EventObserver { t ->
            startActivity(ImagesActivity.buildIntent(this, t.breed, t.subbreed))
        })
    }
}
