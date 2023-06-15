package com.example.myapplication

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout


class ToolBarBehavior : CoordinatorLayout.Behavior<LinearLayout>() {


//    override fun onTouchEvent(parent: CoordinatorLayout, child: LinearLayout, ev: MotionEvent): Boolean {
//        child.findViewById<TextView>(R.id.tvLabel)?.text = ev.action.toString() + "|" + ev.x.toInt() + "|" + ev.y.toInt()
//        return false
//    }
//
//    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: LinearLayout, ev: MotionEvent): Boolean {
//        //Hàm này nhận sự kiện Touch (down) ban đầu nếu nhấn trong CoordinatorLayout
//        //Nếu thiết lập trả về true thì onToucheEvent sẽ nhận các sự kiện
//        //Tiếp theo và các View con khác không nhận được Touch
//        child.findViewById<TextView>(R.id.tvLabel)?.text = ev.action.toString() + "|" + ev.x.toInt() + "|" + ev.y.toInt()
//        return true
//    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: LinearLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        onNestedPreScrollOffset = overallYScroll
        return true
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {
        return dependency.id == R.id.appBar
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: LinearLayout, dependency: View): Boolean {

        return false
    }


    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: LinearLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        overallYScroll += dyConsumed
        val offsetOnScroll = overallYScroll - onNestedPreScrollOffset
        Log.i("check","overallXScroll->" + overallYScroll)
        child.translationY = offsetOnScroll*-1F
        child.findViewById<TextView>(R.id.tvLabel)?.text = "$dyConsumed|$overallYScroll|$offsetOnScroll"

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }
    private var overallYScroll = 0
    private var topBarOffset = 0
    private var onNestedPreScrollOffset = 0

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, target: View, type: Int) {
        child.findViewById<TextView>(R.id.tvLabel)?.text = "StopScroll"
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: LinearLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        Log.i("check","onNestedPreScroll->" + overallYScroll)
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: LinearLayout, target: View, velocityX: Float, velocityY: Float): Boolean {
        Log.d("ToolBarBehavior", "onNestedPreFling")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: LinearLayout,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.d("ToolBarBehavior", "onNestedFling")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }
}