package com.rosberry.pagination.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.pagination.R
import com.rosberry.pagination.data.MoviesApi
import com.rosberry.pagination.data.MoviesRepository
import com.rosberry.pagination.presentation.MoviesPresenter
import com.rosberry.pagination.presentation.MoviesView
import com.rosberry.pagination.system.MovieItems
import com.rosberry.pagination.system.gone
import com.rosberry.pagination.system.show
import kotlinx.android.synthetic.main.activity_movies.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoviesActivity : MvpAppCompatActivity(), MoviesView {

    @InjectPresenter
    lateinit var presenter: MoviesPresenter

    private val viewHandler = Handler()

    private val moviesAdapter by lazy { MoviesAdapter(emptyList()) { presenter.loadNextPage() } }

    @ProvidePresenter
    fun providePresenter(): MoviesPresenter {
        val api = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://afisha-omsk.ru")
            .build()
            .create(MoviesApi::class.java)

        val repository = MoviesRepository(api)
        return MoviesPresenter(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        moviesListView.adapter = moviesAdapter
        refreshMoviesView.setOnRefreshListener { presenter.onRefresh() }
        retryButton.setOnClickListener { presenter.onRefresh() }
    }

    override fun showEmptyProgress(show: Boolean) {
        progressView.visible(show)

        refreshMoviesView.visible(!show)
        viewHandler.post { refreshMoviesView.isRefreshing = false }
    }

    override fun showEmptyError(show: Boolean, errorText: String?) {
        errorTextView.text = errorText
        errorTextView.visible(show)
        retryButton.visible(show)
    }

    override fun showEmptyView(show: Boolean) {
        emptyImageView.visible(show)
    }

    override fun showError(errorDescription: String?) {
        Toast.makeText(baseContext, errorDescription, Toast.LENGTH_LONG)
            .show()
    }

    override fun showData(show: Boolean, data: MovieItems) {
        if (show) moviesAdapter.showData(data)
        moviesListView.visible(show)
    }

    override fun showRefreshProgress(show: Boolean) {
        viewHandler.post { refreshMoviesView.isRefreshing = show }
    }

    override fun showPageProgress(show: Boolean) {
        viewHandler.post { moviesAdapter.showProgress(show) }
    }

    private fun View.visible(show: Boolean) {
        if (show) this.show()
        else this.gone()
    }
}
