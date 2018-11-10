package com.matheusvillela.hiperbolao.mvvm.standings

import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_round_result.*
import kotlinx.android.synthetic.main.fragment_standings.*


class RoundResultDialogFragment : DialogFragment() {
    private lateinit var viewModel: StandingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Light_NoTitleBar)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = obtainViewModel(StandingsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_round_result, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_round_result_recycler.setHasFixedSize(true)
        fragment_round_result_recycler.layoutManager = LinearLayoutManager(activity)
        fragment_round_result_recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val roundResultAdapter = RoundResultAdapter()
        fragment_round_result_recycler.adapter = roundResultAdapter
        viewModel.selectedRoundResult.observe(this, Observer {
            if (it != null) {
                roundResultAdapter.setBets(it.bets)
            }
        })
        viewModel.selectedRoundResultPlayer.observe(this, Observer {
            if (it != null) {
                fragment_round_result_title.text = it.name
            }
        })
    }
}