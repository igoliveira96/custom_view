package com.example.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customview.databinding.ProgressButtonBinding

// @JvmOverloads -> Automaticamente gera todos os construtores necessários em Java
class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAtr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAtr) {

    private var title: String? = null
    private var loadingTitle: String? = null

    private val binding = ProgressButtonBinding
        .inflate(LayoutInflater.from(context), this, true) // Irá substituir algumas propriedas do root xml

    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value) {
            field = value
            refreshState()
        }

    init {
        setLayout(attrs)
        refreshState()
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let { attributeSet ->
            val attributes = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ProgressButton
            )

            setBackgroundResource(R.drawable.progress_button_background)

            val titleResID = attributes.getResourceId(R.styleable.ProgressButton_title, 0)
            if (titleResID != 0) {
                title = context.getString(titleResID)
            }

            val loadingTitleResID = attributes.getResourceId(R.styleable.ProgressButton_loading_title, 0)
            if (titleResID != 0) {
                loadingTitle = context.getString(loadingTitleResID)
            }

            attributes.recycle()
        }
    }

    private fun refreshState() {
        isEnabled = state.isEnabled
        isClickable = state.isEnabled
        refreshDrawableState()

        binding.textTitle.run {
            isEnabled = state.isEnabled
            isClickable = state.isEnabled
        }

        binding.progressButton.visibility = state.progressVisibility

        when(state) {
            ProgressButtonState.Normal -> binding.textTitle.text = title
            ProgressButtonState.Loading -> binding.textTitle.text = loadingTitle
        }
    }

    fun setLoading() {
        state = ProgressButtonState.Loading
    }

    fun setNormal() {
        state = ProgressButtonState.Normal
    }

    sealed class ProgressButtonState(val isEnabled: Boolean, val progressVisibility: Int) {
        object Normal : ProgressButtonState(true, View.GONE)
        object Loading : ProgressButtonState(false, View.VISIBLE)
    }

}