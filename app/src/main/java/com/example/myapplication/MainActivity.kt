package com.example.myapplication


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val content = findViewById<RecyclerView>(R.id.rvContent)
        val appBar = findViewById<LinearLayout>(R.id.appBar)
        content?.adapter = SimpleAdapter()

        (appBar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolBarBehavior()
    }
}

class SimpleAdapter() : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 100

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(position: Int) {
        view.findViewById<TextView>(R.id.tvText).text = "Text $position"
    }
}