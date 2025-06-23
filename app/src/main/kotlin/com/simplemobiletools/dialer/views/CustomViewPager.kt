package com.simplemobiletools.dialer.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * A custom ViewPager implementation that replaces the functionality of MyViewPager
 * without the dependency on rtl-viewpager.
 */
class CustomViewPager : ViewPager {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    // Flag to enable/disable swiping
    private var isSwipingEnabled = true

    // For RTL support, we'll handle the direction in our adapter and fragments
    private var isRtl = false

    /**
     * Set whether swiping is enabled or not
     */
    fun setSwipingEnabled(enabled: Boolean) {
        isSwipingEnabled = enabled
    }

    /**
     * Set RTL mode (for right-to-left languages)
     */
    fun setRtl(rtl: Boolean) {
        if (isRtl != rtl) {
            isRtl = rtl
            // If we change the RTL setting, we need to adjust the current item
            if (adapter != null && adapter?.count ?: 0 > 0) {
                val newPosition = getConvertedPosition(currentItem)
                setCurrentItem(newPosition, false)
            }
        }
    }

    /**
     * Convert position based on RTL setting
     */
    private fun getConvertedPosition(position: Int): Int {
        return if (isRtl && adapter != null) {
            adapter!!.count - 1 - position
        } else {
            position
        }
    }

    /**
     * Override setCurrentItem to handle RTL conversion
     */
    override fun setCurrentItem(position: Int, smoothScroll: Boolean) {
        super.setCurrentItem(getConvertedPosition(position), smoothScroll)
    }

    /**
     * Override getCurrentItem to handle RTL conversion
     */
    override fun getCurrentItem(): Int {
        val originalPosition = super.getCurrentItem()
        return if (isRtl && adapter != null) {
            adapter!!.count - 1 - originalPosition
        } else {
            originalPosition
        }
    }

    /**
     * Override setCurrentItem to handle RTL conversion (single parameter version)
     */
    override fun setCurrentItem(position: Int) {
        super.setCurrentItem(getConvertedPosition(position))
    }

    /**
     * Handle touch events to enable/disable swiping
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isSwipingEnabled && super.onInterceptTouchEvent(ev)
    }

    /**
     * Handle touch events to enable/disable swiping
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return isSwipingEnabled && super.onTouchEvent(ev)
    }
}
