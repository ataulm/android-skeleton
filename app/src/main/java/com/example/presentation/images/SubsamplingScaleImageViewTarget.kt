package com.example.presentation.images

import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import java.io.File

// https://github.com/davemorrissey/subsampling-scale-image-view/issues/234#issuecomment-515853876
internal class SubsamplingScaleImageViewTarget(view: SubsamplingScaleImageView)
    : CustomViewTarget<SubsamplingScaleImageView, File>(view) {
    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        view.setImage(ImageSource.uri(Uri.fromFile(resource)))
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        // TODO: add error loading image
    }

    override fun onResourceCleared(placeholder: Drawable?) {
        // TODO: add placeholder image
    }
}
