package com.matheusvillela.hiperbolao.mvvm.playerbets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.hiperbolao.model.Player
import com.matheusvillela.hiperbolao.model.PlayerBetsInfo
import com.matheusvillela.hiperbolao.model.RoundResult
import com.matheusvillela.hiperbolao.model.Standing
import com.matheusvillela.hiperbolao.shared.Api
import com.matheusvillela.hiperbolao.shared.ViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PlayerBetsViewModel @Inject constructor(app: Application,
                                              private val api: Api) : AndroidViewModel(app) {
    private val disposables = CompositeDisposable()
    val playerBetsInfo = MutableLiveData<PlayerBetsInfo>()
    val viewStatus = MutableLiveData<ViewStatus>()
    fun loadPlayerBetsInfo(playerId: Int) {
        viewStatus.value = ViewStatus.LOADING
        disposables.add(api.playerBetsInfo(playerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    playerBetsInfo.value = result
                    viewStatus.value = ViewStatus.READY
                }, {
                    Timber.d(it, "error getting player info")
                    viewStatus.value = ViewStatus.ERROR
                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
