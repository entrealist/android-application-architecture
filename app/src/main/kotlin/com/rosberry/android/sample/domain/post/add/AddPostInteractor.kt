package com.rosberry.android.sample.domain.post.add

import com.rosberry.android.sample.di.post.PostScope
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * Created by neestell on 02.10.2018.
 * Rosberry Ltd
 */
@PostScope
class AddPostInteractor @Inject constructor() {

    private val onTitleChange : PublishSubject<String> = PublishSubject.create<String>()
    private val onDescriptionChange : PublishSubject<String> = PublishSubject.create<String>()

    fun listenTitleChange(): Observable<String> {
        return onTitleChange
    }

    fun listenDescriptionChange(): Observable<String> {
        return onDescriptionChange
    }

    fun changeTitle(text: String){
        onTitleChange.onNext(text)
    }

    fun changeDescription(text: String){
        onDescriptionChange.onNext(text)
    }

}