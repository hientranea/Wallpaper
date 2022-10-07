package com.hientran.wallpaper.common.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

val glideCrossFadeFactory =
    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()!!

fun ImageView.loadWithGlide(url: String) {
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(glideCrossFadeFactory))
        .into(this)
}

fun ImageView.loadWithGlide(url: String, onLoadFailed: () -> Unit, onResourceReady: () -> Unit) {
    Glide.with(this.context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(glideCrossFadeFactory))
        .listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadFailed()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onResourceReady()
                return false
            }
        })
        .into(this)
}

fun Context.loadBitmapWithGlide(
    url: String,
    onLoadFailed: () -> Unit,
    onResourceReady: (bitmap: Bitmap?) -> Unit
) {
    Glide.with(this)
        .asBitmap()
        .load(url)
        .listener(object: RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadFailed()
                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onResourceReady(resource)
                return false
            }
        })
        .submit()
}
