package com.example.myapplication

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.LayoutHeaderBinding
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {

    private lateinit var rcvContent: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        rcvContent = findViewById(R.id.rcvContent)
        rcvContent.layoutManager = LinearLayoutManager(this)
        val adapter = ContentAdapter((0 until 100).map { Random.nextInt(100) })
        rcvContent.addItemDecoration(ItemDecorator(adapter, root = rcvContent))
        rcvContent.adapter = adapter
    }
}


class ItemDecorator(private val adapter: ContentAdapter, private val root: View) :
    RecyclerView.ItemDecoration() {

    private val headerBinding by lazy { LayoutHeaderBinding.inflate(LayoutInflater.from(root.context)) }

    private val headerView: View
        get() = headerBinding.root

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)

        val topChild = parent.getChildAt(0)

        parent.getChildAdapterPosition(topChild)
            .let { topChildPosition ->
                if (topChildPosition >= adapter.headerStickFrom()) {
                    layoutHeaderView(topChild)
                    canvas.drawHeaderView()
                }
            }
    }

    private fun layoutHeaderView(topView: View?) {
        topView?.let {
            headerView.measure(
                View.MeasureSpec.makeMeasureSpec(topView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            )
            headerView.layout(topView.left, 0, topView.right, headerView.measuredHeight)
        }
    }

    private fun Canvas.drawHeaderView() {
        save()
        headerView.draw(this)
        restore()
    }
}