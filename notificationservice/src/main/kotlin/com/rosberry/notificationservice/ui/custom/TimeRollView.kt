package com.rosberry.notificationservice.ui.custom

import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.rosberry.notificationservice.R
import com.rosberry.notificationservice.extension.alsoOnLaid
import com.rosberry.notificationservice.model.TimeRollItem
import kotlinx.android.synthetic.main.i_time_roll.view.*

/**
 * @author mmikhailov on 28/02/2019.
 */
class TimeRollView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private var textOfLast: String = "Add more"
    private var textOfNotLast: String = "Remove"
    @ColorRes
    private var textOfLastColorRes: Int = R.color.colorAccent
    @ColorRes
    private var textOfNotLastColorRes: Int = R.color.colorPrimaryDark

    private var changeTimeListener: ((Int, Int, Int) -> Unit)? = null
    private var buttonClickListener: ((Int) -> Unit)? = null

    init {
        orientation = VERTICAL
    }

    fun setListeners(changeTimeListener: ((Int, Int, Int) -> Unit),
                     buttonClickListener: ((Int) -> Unit)) {
        this.changeTimeListener = changeTimeListener
        this.buttonClickListener = buttonClickListener
    }

    fun updateItems(newItems: List<TimeRollItem>, onAddedListener: (() -> Unit)? = null) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    getChildAt(oldItemPosition).tag == newItems[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    getChildAt(oldItemPosition).timeText.text == newItems[newItemPosition].textValue

            override fun getOldListSize() = childCount

            override fun getNewListSize() = newItems.size
        })
            .dispatchUpdatesTo(object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {
                    addItems(newItems, position, count)
                    onAddedListener?.invoke()
                }

                override fun onRemoved(position: Int, count: Int) {
                    removeItems(position, count)
                }

                override fun onChanged(position: Int, count: Int, payload: Any?) {
                    setItemsText(newItems, position, count)
                }

                override fun onMoved(fromPosition: Int, toPosition: Int) {
                }
            })
    }

    private fun addItems(items: List<TimeRollItem>, position: Int, count: Int) {
        for (i in position until position + count) {
            val item = items[i]
            createItemView(item, item.id == items.last().id)
                .also { addView(it) }
        }

        invalidateCompoundButtons()
    }

    private fun removeItems(position: Int, count: Int) {
        for (i in position until position + count) {
            findViewWithTag<View>(getChildAt(i).tag as Int).also {
                removeView(it)
            }
        }

        invalidateCompoundButtons()
    }

    private fun setItemsText(items: List<TimeRollItem>, position: Int, count: Int) {
        for (i in position until position + count) {
            findViewWithTag<View>(items[i].id)
                ?.timeText
                ?.apply { text = items[i].textValue }
        }
    }

    @Suppress("SameParameterValue")
    private fun createItemView(model: TimeRollItem, lastItem: Boolean) =
            View.inflate(context, R.layout.i_time_roll, null)
                .apply {
                    tag = model.id

                    with(timeText) {
                        text = model.textValue
                        setOnClickListener {
                            showTimePickerDialog(model.id, model.hour, model.minute)
                        }
                    }

                    with(compoundBtn) {
                        text = if (lastItem) textOfLast else textOfNotLast
                        setTextColor(ContextCompat.getColorStateList(context,
                                if (lastItem) textOfLastColorRes else textOfNotLastColorRes))
                        setOnClickListener { buttonClickListener?.invoke(model.id) }
                    }
                }

    private fun showTimePickerDialog(itemTag: Int, setHour: Int, setMinute: Int) {
        TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    changeTimeListener?.invoke(itemTag, hourOfDay, minute)
                },
                setHour,
                setMinute,
                true
        ).show()
    }

    private fun invalidateCompoundButtons() {
        var btnMaxWidth = 0
        val buttonViews = children.map { it.compoundBtn }
            .toList()

        buttonViews.forEachIndexed { index, textView ->
            invalidateCompoundText(textView, index == buttonViews.lastIndex) {
                if (textView.width > btnMaxWidth) { // find widest button
                    btnMaxWidth = textView.width
                }
            }
        }

        buttonViews.last().alsoOnLaid {
            if (btnMaxWidth > 0) {
                buttonViews.forEach {
                    it.apply {
                        layoutParams = layoutParams.apply { width = btnMaxWidth }
                    }
                }
            }
        }
    }

    private fun invalidateCompoundText(textView: TextView, lastItem: Boolean, onTextWidth: (Int) -> Unit) {
        val oldText = textView.text
        val newText = if (lastItem) textOfLast else textOfNotLast

        if (oldText != newText) {
            val newColor = if (lastItem) textOfLastColorRes else textOfNotLastColorRes

            AlphaAnimation(1.0f, 0.0f).apply {
                duration = 200
                repeatCount = 1
                repeatMode = Animation.REVERSE
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                        textView.text = newText
                        textView.setTextColor(ContextCompat.getColorStateList(context, newColor))
                        textView.isEnabled = true
                        onTextWidth(textView.width)
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }
                })

                textView.startAnimation(this)
                textView.isEnabled = false
            }
        } else {
            textView.alsoOnLaid {
                onTextWidth(it.width)
            }
        }
    }
}