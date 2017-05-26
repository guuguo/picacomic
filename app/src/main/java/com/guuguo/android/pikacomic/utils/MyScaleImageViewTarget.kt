package com.guuguo.android.pikacomic.utils;

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.SquaringDrawable
import com.bumptech.glide.request.target.Target
import com.guuguo.android.lib.view.RatioImageView

/**
 * mimi 创造于 2017-03-13.
 * 项目 order
 */
class MyScaleImageViewTarget : ImageViewTarget<GlideDrawable> {
    val scaleType = ImageView.ScaleType.CENTER_CROP

    override fun onLoadStarted(placeholder: Drawable?) {
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        super.onLoadStarted(placeholder)
    }

    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        super.onLoadFailed(e, errorDrawable)
    }

    private val SQUARE_RATIO_MARGIN = 0.05f
    private var maxLoopCount: Int = 0
    private var resource: GlideDrawable? = null

    constructor(view: RatioImageView, maxLoopCount: Int = GlideDrawable.LOOP_FOREVER) : super(view) {
        this.maxLoopCount = maxLoopCount
    }

    override fun onResourceReady(resource: GlideDrawable, animation: GlideAnimation<in GlideDrawable>?) {
        view.scaleType = scaleType
        val height = resource.intrinsicHeight
        val width = resource.intrinsicWidth
        (view as RatioImageView).setOriginalSize(width, height)
        var resource = resource
        if (!resource.isAnimated) {
            val viewRatio = view.getWidth() / view.getHeight().toFloat()
            val drawableRatio = resource.intrinsicWidth / resource.intrinsicHeight.toFloat()
            if (Math.abs(viewRatio - 1f) <= SQUARE_RATIO_MARGIN && Math.abs(drawableRatio - 1f) <= SQUARE_RATIO_MARGIN) {
                resource = SquaringDrawable(resource, view.getWidth())
            }
        }
        this.resource = resource
        super.onResourceReady(resource, animation)

        resource.setLoopCount(maxLoopCount)
        resource.start()
    }

    override fun getSize(cb: SizeReadyCallback?) {
        cb?.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    }

    override fun setResource(resource: GlideDrawable) {
        view.setImageDrawable(resource)
    }

    override fun onStart() {
        if (resource != null) {
            resource!!.start()
        }
    }

    override fun onStop() {
        if (resource != null) {
            resource!!.stop()
        }
    }
}