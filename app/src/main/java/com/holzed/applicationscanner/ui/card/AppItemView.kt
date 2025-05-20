package com.holzed.applicationscanner.ui.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.holzed.applicationscanner.data.model.AppItemModel
import com.holzed.applicationscanner.databinding.ItemAppBinding

class AppItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: ItemAppBinding = LayoutInflater.from(context).let {
        ItemAppBinding.inflate(it, this, true)
    }

    fun configure(model: AppItemModel) {
        with(binding) {
            titleTextView.text = model.title
        }
    }
}
