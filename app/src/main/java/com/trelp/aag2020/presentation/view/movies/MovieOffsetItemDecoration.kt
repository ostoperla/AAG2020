package com.trelp.aag2020.presentation.view.movies

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Use only for VERTICAL orientation
 */
class MovieOffsetItemDecoration(
    private val offset: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val rect = Rect(0, 0, 0, 0)
        val childPosition = parent.getChildAdapterPosition(view)

        val childIsInLeftmostColumn = childPosition.rem(SPAN_COUNT) == 0
        val childIsInFirstRow = childPosition < SPAN_COUNT

        if (childIsInLeftmostColumn) {
            rect.right = offset.div(SPAN_COUNT)
        } else {
            rect.left = offset.div(SPAN_COUNT)
        }

        if (!childIsInFirstRow) {
            rect.top = offset
        }

        Log.d(this.javaClass.simpleName, "$childPosition/$rect")
        outRect.set(rect)
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}