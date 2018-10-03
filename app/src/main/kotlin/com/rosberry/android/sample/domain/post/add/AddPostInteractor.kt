package com.rosberry.android.sample.domain.post.add

import com.rosberry.android.sample.data.persistence.network.repository.PostRepository
import com.rosberry.android.sample.di.post.PostScope
import com.rosberry.android.sample.entity.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by neestell on 02.10.2018.
 * Rosberry Ltd
 */
@PostScope
class AddPostInteractor @Inject constructor(val postRepository: PostRepository) {

    private val onTitleChange: PublishSubject<String> = PublishSubject.create<String>()
    private val onDescriptionChange: PublishSubject<String> = PublishSubject.create<String>()
    private val onPostContextIdChange: ReplaySubject<Int> = ReplaySubject.create<Int>()

    fun listenTitleChange(): Observable<String> =  onTitleChange

    fun listenDescriptionChange(): Observable<String> = onDescriptionChange

    fun changeTitle(text: String) = onTitleChange.onNext(text)

    fun changeDescription(text: String) = onDescriptionChange.onNext(text)

    fun changeContextId(contextId: Int) = onPostContextIdChange.onNext(contextId)

    fun sendPost(post: Post): Single<Post> = postRepository.addPost(post)

    fun generateContextId() =
            Observable.timer(1, TimeUnit.SECONDS)
                .map { it -> (Math.random() * 1000).toInt() }

    fun listenContextId(): Observable<Int> = onPostContextIdChange

}