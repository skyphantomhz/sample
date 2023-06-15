package com.example.myapplication

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

class CustomShadow: ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        val rect = Rect(0, 0, view.width, view.height)
        outline.setRoundRect(rect, view.resources.getDimension(R.dimen.dp_4))
        view.clipToOutline = true
    }
}