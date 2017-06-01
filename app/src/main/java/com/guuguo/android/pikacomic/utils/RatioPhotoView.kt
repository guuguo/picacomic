package com.guuguo.android.lib.view

import android.content.Context
import android.util.AttributeSet
import com.bm.library.PhotoView
import com.guuguo.android.R

/**
 * 一个能保持比例的 ImageView
 * TODO: 暂时只支持维持宽度适应高度
 *
 * @author XiNGRZ
 */
class RatioPhotoView : PhotoView {

    private var originalWidth: Int = 0
    private var originalHeight: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr(context, attrs)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
            originalWidth = typedArray.getDimensionPixelSize(R.styleable.RatioImageView_riv_origin_width, 0)
            originalHeight = typedArray.getDimensionPixelSize(R.styleable.RatioImageView_riv_origin_height, 0)
            typedArray.recycle()
        }
    }

    fun setOriginalSize(originalWidth: Int, originalHeight: Int) {
        this.originalWidth = originalWidth
        this.originalHeight = originalHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (originalWidth > 0 && originalHeight > 0) {
            val ratio = originalWidth.toFloat() / originalHeight.toFloat()

            val width = MeasureSpec.getSize(widthMeasureSpec)
            var height = MeasureSpec.getSize(heightMeasureSpec)

            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (width.toFloat() / ratio).toInt()
            }

            setMeasuredDimension(width, height)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }

}
