package com.guuguo.android.pikacomic.utils;

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BitmapImageViewTarget
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
class ScaleContentImageViewTarget(view: RatioImageView, private var item: ImageEntity, var comicId: String) : BitmapImageViewTarget(view) {
    val scaleType = ImageView.ScaleType.CENTER_CROP
    override fun onLoadStarted(placeholder: Drawable?) {
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        super.onLoadStarted(placeholder)
    }

    override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
        view.scaleType = ImageView.ScaleType.CENTER_INSIDE
        super.onLoadFailed(e, errorDrawable)
    }

    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
        super.onResourceReady(resource, glideAnimation)
    }

    override fun setResource(resource: Bitmap) {
        val height = resource.height
        val width = resource.width
        (view as RatioImageView).setOriginalSize(width, height)
        Completable.create {
            val fop: FileOutputStream = FileOutputStream(getFileDir() + "/" + item.media!!.path)
            resource.compress(Bitmap.CompressFormat.JPEG, 100, fop)
            fop.close()

            item.media!!.isDownload = true
            val epEntity = EpEntity.get(comicId, item.ep)
            epEntity!!.downloadCount++
            
            UOrm.db().update(item.media!!)
            UOrm.db().update(epEntity)


        }.subscribeOn(Schedulers.io()).subscribe({}, {
            e ->
            e.printStackTrace()
        })
        view.setImageBitmap(resource)

    }

}