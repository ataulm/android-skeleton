package com.example.presentation.images

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

internal class ImagesActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val breedsRepository = (application as PupzApplication).provideRepository()
        val getImagesUsecase = GetImagesUsecase(breedsRepository)
        // TODO: use ViewModelProviders (or whatever is not deprecated now)
        ImagesViewModel(getImagesUsecase, intent.getBreed(), intent.getSubbreed())
    }

    private val imagesAdapter = ImagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        collapsingToolbarLayout.title = intent.getSubbreed()?.name ?: intent.getBreed().name
        recyclerView.adapter = imagesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        viewModel.images.observe(this, NonNullObserver { t ->
            imagesAdapter.submitList(t)
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
