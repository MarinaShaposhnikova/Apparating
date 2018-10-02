package com.meier.marina.mytaxi.map

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.meier.marina.mytaxi.R
import com.meier.marina.network.entity.Vehicle
import org.koin.android.architecture.ext.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.android.gms.maps.model.BitmapDescriptor
import com.meier.marina.mytaxi.State
import com.meier.marina.mytaxi.utils.toast
import com.meier.marina.network.entity.FleetType


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var mainMarker: MarkerOptions

    private val viewModel by viewModel<MapViewModel>()
    private val taxiBitmap by lazy { getBitmapDescriptor(R.drawable.ic_taxi) }
    private val groupBitmap by lazy { getBitmapDescriptor(R.drawable.ic_group) }

    companion object {
        const val KEY_VEHICLE = "KEY_VEHICLE"

        @JvmStatic
        fun getIntent(context: Context, vehicle: Vehicle) = Intent(context, MapsActivity::class.java)
            .putExtra(KEY_VEHICLE, vehicle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.liveData.observe(this, Observer {
            when (it) {
                is State.Success -> {
                    map.clear()
                    it.list.forEach { addMarker(it) }
                    map.addMarker(mainMarker).showInfoWindow()
                }
                is State.Error -> toast(it.message)
                null -> {
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true

        val vehicle = intent.getParcelableExtra<Vehicle>(KEY_VEHICLE)
        val coordinate = vehicle.coordinate

        mainMarker = addMarker(vehicle).apply { map.addMarker(this).showInfoWindow() }

        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(coordinate.latitude, coordinate.longitude)))
        map.animateCamera(CameraUpdateFactory.zoomTo(12.0f), 2000, null)

        map.setOnCameraIdleListener {
            val latLngBounds = map.projection.visibleRegion.latLngBounds
            viewModel.onCoordinatesChange(latLngBounds)
        }

        map.setOnMarkerClickListener {
            it.tag.apply {
                if (this is MarkerOptions) {
                    mainMarker = this
                }
            }
            false
        }
    }

    private fun addMarker(vehicle: Vehicle): MarkerOptions {
        val coordinate = vehicle.coordinate
        val marker = LatLng(coordinate.latitude, coordinate.longitude)
        val bitmapDescriptor = when (vehicle.fleetType) {
            FleetType.POOLING -> groupBitmap
            FleetType.TAXI -> taxiBitmap
        }
        return MarkerOptions().position(marker).title(vehicle.fleetType.name).icon(bitmapDescriptor)
            .apply { map.addMarker(this).tag = this }
    }

    private fun getBitmapDescriptor(id: Int): BitmapDescriptor {
        val vectorDrawable = getDrawable(id)
        vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bm = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }
}
