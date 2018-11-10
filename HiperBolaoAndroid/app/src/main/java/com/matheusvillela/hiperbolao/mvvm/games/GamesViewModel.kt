package com.matheusvillela.hiperbolao.mvvm.games

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.hiperbolao.model.Game
import com.matheusvillela.hiperbolao.model.Standing
import com.matheusvillela.hiperbolao.shared.ViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import com.matheusvillela.hiperbolao.shared.Api

class GamesViewModel @Inject constructor(app: Application,
                                         private val api: Api) : AndroidViewModel(app) {
    private val disposables = CompositeDisposable()
    val games: MutableLiveData<List<Game>> = MutableLiveData()
    val viewStatus: MutableLiveData<ViewStatus> = MutableLiveData()

    fun loadGames() {
        viewStatus.value = ViewStatus.LOADING
        disposables.add(api.games()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    games.value = result.sortedBy { it.start }
                    viewStatus.value = ViewStatus.READY
                }, {
                    Timber.d(it, "error getting games")
                    viewStatus.value = ViewStatus.ERROR
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}