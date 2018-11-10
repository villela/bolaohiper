package com.matheusvillela.hiperbolao.mvvm.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.mvvm.playerbets.PlayerBetsFragment
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    private var playerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val extras = intent.extras
        playerId = extras?.getInt("id") ?: throw RuntimeException()
        extras.getString("name")?.let { activity_player_toolbar.title = it }
        setSupportActionBar(activity_player_toolbar)

        activity_player_pager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val fragment = PlayerBetsFragment()
                val bundle = Bundle()
                bundle.putInt("id", playerId)
                fragment.arguments = bundle
                return fragment
            }

            override fun getCount(): Int = 1
        }
    }
}
