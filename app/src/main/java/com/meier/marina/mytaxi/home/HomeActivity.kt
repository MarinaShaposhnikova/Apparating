package com.meier.marina.mytaxi.home

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.meier.marina.mytaxi.R
import com.meier.marina.mytaxi.map.MapsActivity
import com.meier.marina.network.entity.Vehicle
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.architecture.ext.viewModel
import com.meier.marina.mytaxi.State
import com.meier.marina.mytaxi.utils.toast

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private val adapter by lazy { VehicleAdapter(::onItemClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        listVehicle.layoutManager = LinearLayoutManager(this)
        listVehicle.adapter = adapter

        viewModel.liveData.observe(this, Observer {
            when (it) {
                is State.Success -> adapter.updateList(it.list)
                is State.Error -> toast(it.message)
                null -> {
                }
            }
        })
    }

    private fun onItemClick(vehicle: Vehicle) {
        startActivity(MapsActivity.getIntent(this, vehicle))
    }
}
