package com.matheusvillela.hiperbolao.mvvm.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.hiperbolao.model.Game
import com.matheusvillela.hiperbolao.model.GameWithBets
import com.matheusvillela.hiperbolao.model.Standing
import com.matheusvillela.hiperbolao.shared.ViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import com.matheusvillela.hiperbolao.shared.Api

class GameViewModel @Inject constructor(app: Application,
                                        private val api: Api) : AndroidViewModel(app) {
    private val disposables = CompositeDisposable()
    val game: MutableLiveData<GameWithBets> = MutableLiveData()
    val viewStatus: MutableLiveData<ViewStatus> = MutableLiveData()

    fun loadGame(gameId: Int) {
        viewStatus.value = ViewStatus.LOADING
        disposables.add(api.gameWithBets(gameId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    game.value = result
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