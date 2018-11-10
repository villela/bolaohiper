package com.matheusvillela.hiperbolao.mvvm.standings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matheusvillela.hiperbolao.model.Player
import com.matheusvillela.hiperbolao.model.RoundResult
import com.matheusvillela.hiperbolao.model.Standing
import com.matheusvillela.hiperbolao.shared.Api
import com.matheusvillela.hiperbolao.shared.ViewStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class StandingsViewModel @Inject constructor(app: Application,
                                             private val api: Api) : AndroidViewModel(app) {
    private val disposables = CompositeDisposable()
    val standings = MutableLiveData<List<Standing>>()
    val viewStatus = MutableLiveData<ViewStatus>()
    val selectedRoundResult = MutableLiveData<RoundResult>()
    val selectedRoundResultPlayer = MutableLiveData<Player>()
    fun loadStandings() {
        viewStatus.value = ViewStatus.LOADING
        disposables.add(api.standings()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    standings.value = result.sortedBy { it.position }
                    viewStatus.value = ViewStatus.READY
                }, {
                    Timber.d(it, "error getting standings")
                    viewStatus.value = ViewStatus.ERROR
                }))
    }

    fun loadRoundResult(roundResult: RoundResult, player : Player) {
        selectedRoundResult.value = roundResult
        selectedRoundResultPlayer.value = player
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
