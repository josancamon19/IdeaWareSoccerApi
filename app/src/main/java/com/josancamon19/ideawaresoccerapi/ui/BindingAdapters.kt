package com.josancamon19.ideawaresoccerapi.ui

import android.view.View.VISIBLE
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.josancamon19.ideawaresoccerapi.R

@BindingAdapter("setColorFromState")
fun bindDateColorFromState(textView: TextView, state: String?) {
    state?.let {
        if (state == "postponed") {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.highLightColor))
        }
    }
}

@BindingAdapter("setState")
fun bindState(textView: TextView, state: String?) {
    state?.let {
        if (state == "postponed") {
            textView.visibility = VISIBLE
        }
    }
}
