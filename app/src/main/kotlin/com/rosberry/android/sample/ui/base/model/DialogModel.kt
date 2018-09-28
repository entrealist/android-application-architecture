package com.rosberry.android.sample.ui.base.model

import android.support.annotation.StringRes

/**
 * Created by dmitry on 30.10.18.
 */
class DialogModel {
    @StringRes
    var titleId: Int = 0
        private set
    var content: CharSequence = ""
        private set
    @StringRes
    var contentId: Int = 0
        private set
    var positive: Int = 0
        private set
    var negative: Int = 0
        private set
    var neutral: Int = 0
        private set

    var positiveListener: ((Any) -> Unit?)? = null
        private set
    var negativeListener: ((Any) -> Unit?)? = null
        private set
    var neutralListener: ((Any) -> Unit?)? = null
        private set
    var dismissListener: ((Any) -> Unit?)? = null
        private set

    var isCancelable: Boolean = false
        private set

    val max: Int
        get() = 100

    class Builder {

        private val dialogModel = DialogModel()

        fun title(title: Int): Builder {
            dialogModel.titleId = title

            return this
        }

        fun content(content: CharSequence): Builder {
            dialogModel.content = content

            return this
        }

        fun contentId(@StringRes contentId: Int): Builder {
            dialogModel.contentId = contentId

            return this
        }

        fun positive(positive: Int): Builder {
            dialogModel.positive = positive

            return this
        }

        fun negative(negative: Int): Builder {
            dialogModel.negative = negative

            return this
        }

        fun neutral(neutral: Int): Builder {
            dialogModel.neutral = neutral

            return this
        }

        fun positiveListener(listener: (result: Any) -> Unit): Builder {
            dialogModel.positiveListener = listener

            return this
        }

        fun negativeListener(listener: (result: Any) -> Unit): Builder {
            dialogModel.negativeListener = listener

            return this
        }

        fun neutralListener(listener: (result: Any) -> Unit): Builder {
            dialogModel.neutralListener = listener

            return this
        }

        fun dismissListener(listener: (result: Any) -> Unit): Builder {
            dialogModel.dismissListener = listener

            return this
        }

        fun cancellable(b: Boolean): Builder {
            dialogModel.isCancelable = b

            return this
        }

        fun build(): DialogModel {
            return dialogModel
        }

    }
}