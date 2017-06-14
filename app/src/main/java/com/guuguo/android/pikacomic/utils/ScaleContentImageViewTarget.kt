package com.guuguo.android.pikacomic.utils;

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.SquaringDrawable
import com.guuguo.android.lib.utils.FileUtil
import com.guuguo.android.lib.view.RatioImageView
import com.guuguo.android.pikacomic.constant.getFileDir
import com.guuguo.android.pikacomic.db.UOrm
import com.guuguo.android.pikacomic.entity.EpEntity
import com.guuguo.android.pikacomic.entity.ImageEntity
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.io.FileOutputStream


/**
 * mimi 创造于 2017-03-13.
 * 项目 order
 */
class ScaleContentImageViewTarget(view: RatioImageView, private var item: ImageEntity, var comicId: String, var maxLoopCount: Int = GlideDrawable.LOOP_FOREVER) : ImageViewTarget<GlideDrawable>(view) {
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
    private var resource: GlideDrawable? = null

    override fun onResourceReady(resource: GlideDrawable, animation: GlideAnimation<in GlideDrawable>?) {
        view.scaleType = scaleType
        val height = resource.intrinsicHeight
        val width = resource.intrinsicWidth
        (view as RatioImageView).setOriginalSize(width, height)
        var drawable = resource
        val bitmap = FileUtil.drawableToBitmap(drawable)
        Completable.create {
            val fop: FileOutputStream = FileOutputStream(getFileDir() + "/" + item.media!!.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fop)
            fop.close()
            if (!item.media!!.isDownload) {
                item.media!!.isDownload = true
                val epEntity = EpEntity.get(comicId, item.ep)
                epEntity?.let { epEntity.downloadCount++ }
                UOrm.db().update(item.media!!)
                UOrm.db().update(epEntity)
            }
        }.subscribeOn(Schedulers.io()).subscribe({}, {
            e ->
            e.printStackTrace()
        })
        if (!resource.isAnimated) {
            val viewRatio = view.getWidth() / view.getHeight().toFloat()
            val drawableRatio = resource.intrinsicWidth / resource.intrinsicHeight.toFloat()
            if (Math.abs(viewRatio - 1f) <= SQUARE_RATIO_MARGIN && Math.abs(drawableRatio - 1f) <= SQUARE_RATIO_MARGIN) {
                drawable = SquaringDrawable(resource, view.getWidth())
            }
        }
        this.resource = drawable
        super.onResourceReady(resource, animation)
        resource.setLoopCount(maxLoopCount)
        resource.start()
    }

    override fun getSize(cb: SizeReadyCallback?) {
        cb?.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
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