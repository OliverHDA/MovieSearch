package ru.oliverhd.moviesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.oliverhd.moviesearch.AppState
import ru.oliverhd.moviesearch.data.Repository
import ru.oliverhd.moviesearch.data.RepositoryImpl
import ru.oliverhd.moviesearch.data.RetrofitImpl
import ru.oliverhd.moviesearch.model.MovieDetails

class MovieDetailViewModel(
    private val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RetrofitImpl())
) : ViewModel() {

    fun getLiveData() = detailsLiveData

    fun getMovieInfoById(movieID: Int) {
        detailsLiveData.value = AppState.Loading
        repository.getMovieDetail(movieID, callBack)
    }

    private val callBack = object :
        Callback<MovieDetails> {

        override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
            val serverResponse: MovieDetails? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.SuccessDetail(response.body()!!)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    companion object {
        private const val SERVER_ERROR = "Ошибка сервера"
        const val REQUEST_ERROR = "Ошибка запроса на сервер"
    }
}