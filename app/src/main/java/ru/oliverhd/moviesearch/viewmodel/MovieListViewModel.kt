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
import ru.oliverhd.moviesearch.model.MovieListServerResponse

class MovieListViewModel(
    private val mainLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RetrofitImpl())
) : ViewModel() {

    fun getLiveData() = mainLiveData

    fun getPopularMovieList() = repository.getPopularMovieList(callBack)

    private val callBack = object :
        Callback<MovieListServerResponse> {

        override fun onResponse(
            call: Call<MovieListServerResponse>,
            response: Response<MovieListServerResponse>
        ) {
            val serverResponse: MovieListServerResponse? = response.body()
            mainLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.SuccessList(serverResponse.results)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieListServerResponse>, t: Throwable) {
            mainLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    companion object {
        private const val SERVER_ERROR = "Ошибка сервера"
        const val REQUEST_ERROR = "Ошибка запроса на сервер"
    }
}