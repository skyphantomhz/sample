package com.example.myapplication

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.text.Layout
import android.text.SpannedString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.animation.doOnEnd
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans

class ExpandableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatTextView(context, attrs) {
    private var originalText = ""
    private var collapsedText = SpannedString("")


    private val collapseLine = 2
    private var isExpanded = false

    private var onSeeMoreClick: (() -> Unit)? = null

    init {

        justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        maxLines = collapseLine
        setOnClickListener { action.invoke() }
    }

    private val action = {
        if (isExpanded) {
            collapseTextView()
        } else {
            expandTextView()
            this.onSeeMoreClick?.invoke()
        }
        isExpanded = !isExpanded
    }

    fun setText(
        originalText: String?,
        onSeeMoreClick: (() -> Unit)? = null,
        seeMoreSpans: Array<Any> = arrayOf(StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.1f))
    ) {
        this.originalText = originalText ?: ""
        text = originalText
        this.onSeeMoreClick = onSeeMoreClick
        post {
            collapsedText = getTrimmedText(collapseLine = collapseLine, spans = seeMoreSpans)
            collapseTextView()
        }
    }

    private fun expandTextView() {
        text = originalText
        val animation = ObjectAnimator.ofInt(this, maxLinesProperty, lineCount)
        animation.setDuration(ANIMATION_DURATION).start()
    }

    private fun collapseTextView() {
        val animation = ObjectAnimator.ofInt(this, maxLinesProperty, collapseLine)
        animation.setDuration(ANIMATION_DURATION).start()
        animation.doOnEnd {
            text = collapsedText
        }
    }

    companion object {
        private const val maxLinesProperty = "maxLines"
        private const val ANIMATION_DURATION = 200L
    }
}


private fun TextView.getTrimmedText(
    spans: Array<Any> = arrayOf(StyleSpan(Typeface.BOLD), RelativeSizeSpan(1.1f)),
    collapseLine: Int
): SpannedString {
    val seeMoreText = "... Xem Thêm"

    return if (lineCount > collapseLine) {
        buildSpannedString {
            repeat(collapseLine) { lineIndex ->
                if (lineIndex < collapseLine - 1) {
                    append(getLineText(lineIndex))
                } else {
                    append(getLineText(lineIndex).let { it.substring(0, 0.coerceAtLeast(it.length - seeMoreText.length - 10)) })
                }
            }

            inSpans(*spans) {
                append(seeMoreText)
            }
        }
    } else buildSpannedString { append(text) }
}

private fun TextView.getLineText(lineIndex: Int): String {
    val maxIndex = text.length
    return text.substring(layout.getLineStart(lineIndex), Integer.min(maxIndex, layout.getLineEnd(lineIndex)))
}

