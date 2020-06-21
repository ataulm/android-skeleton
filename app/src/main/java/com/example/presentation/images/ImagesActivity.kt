package com.example.presentation.images

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.R
import com.example.domain.Breed
import com.example.domain.GetImagesUsecase
import com.example.domain.Subbreed
import com.example.presentation.EventObserver
import com.example.presentation.NonNullObserver
import com.example.presentation.PupzApplication
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.android.synthetic.main.activity_images.errorTextView
import kotlinx.android.synthetic.main.activity_images.loadingErrorView
import kotlinx.android.synthetic.main.activity_images.recyclerView
import kotlinx.android.synthetic.main.activity_images.retryButton

internal class ImagesActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val breedsRepository = (application as PupzApplication).provideRepository()
        val getImagesUsecase = GetImagesUsecase(breedsRepository)
        @Suppress("UNCHECKED_CAST") val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ImagesViewModel(getImagesUsecase, intent.getBreed(), intent.getSubbreed()) as T
            }
        }
        ViewModelProvider(this, factory).get(ImagesViewModel::class.java)
    }

    private val imagesAdapter = ImagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        recyclerView.adapter = imagesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        viewModel.images.observe(this, NonNullObserver { imagesUiModel ->
            when (imagesUiModel) {
                is ImagesUiModel.Data -> {
                    appBarLayout.visibility = View.VISIBLE
                    collapsingToolbarLayout.title = imagesUiModel.title
                    imagesAdapter.submitList(imagesUiModel.items)
                    loadingErrorView.visibility = View.GONE
                }
                is ImagesUiModel.Error -> {
                    appBarLayout.visibility = View.GONE
                    loadingErrorView.visibility = View.VISIBLE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = imagesUiModel.message
                    if (imagesUiModel.onClickRetry != null) {
                        retryButton.visibility = View.VISIBLE
                        retryButton.setOnClickListener { imagesUiModel.onClickRetry.handler(Unit) }
                    } else {
                        retryButton.visibility = View.INVISIBLE
                    }
                }
                ImagesUiModel.Loading -> {
                    appBarLayout.visibility = View.GONE
                    errorTextView.visibility = View.INVISIBLE
                    retryButton.visibility = View.INVISIBLE
                    loadingErrorView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.events.observe(this, EventObserver {
            showSpotlight(it.spotlightImageUiModel)
        })
    }

    private fun showSpotlight(spotlightImageUiModel: SpotlightImageUiModel) {
        spotlightContainerView.visibility = View.VISIBLE
        Glide.with(spotlightImageView)
                .load(spotlightImageUiModel.url)
                .into(spotlightImageView)
    }

    override fun onBackPressed() {
        if (spotlightContainerView.visibility == View.VISIBLE) {
            spotlightContainerView.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }

    companion object {

        private const val KEY_BREED = "key_breed"
        private const val KEY_SUBBREED = "key_subbreed"

        fun buildIntent(activity: Activity, breed: Breed, subbreed: Subbreed? = null): Intent {
            return Intent(activity, ImagesActivity::class.java)
                    .putExtra(KEY_BREED, breed)
                    .putExtra(KEY_SUBBREED, subbreed)
        }

        private fun Intent.getBreed() = getSerializableExtra(KEY_BREED) as Breed
        private fun Intent.getSubbreed() = getSerializableExtra(KEY_SUBBREED) as? Subbreed
    }
}
