package com.rosberry.android.sample.data.persistence.internal

import android.content.Context
import android.os.Bundle
import com.evernote.android.state.StateSaver
import com.rosberry.android.sample.data.persistence.CommonRepository

abstract class ViewDataRepository: CommonRepository() {

    fun saveInstanceState(bundle: Bundle?) {
        if (bundle != null)
            StateSaver.saveInstanceState(this, bundle)
    }

    fun restoreInstanceState(bundle: Bundle?) {
        StateSaver.restoreInstanceState(this, bundle)
    }

}