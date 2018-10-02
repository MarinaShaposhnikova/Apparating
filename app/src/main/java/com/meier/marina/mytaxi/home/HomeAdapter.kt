package com.meier.marina.mytaxi.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meier.marina.mytaxi.R
import com.meier.marina.network.entity.FleetType
import com.meier.marina.network.entity.Vehicle
import kotlinx.android.synthetic.main.item_vehicle.view.*

class VehicleAdapter(private val clickListener: (vehicle: Vehicle) -> Unit
) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    private val list = mutableListOf<Vehicle>()

    fun updateList(newList: List<Vehicle>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        with(holder.itemView) {
            val item = list[position]
            layout.setOnClickListener { clickListener(item) }
            when (item.fleetType) {
                FleetType.POOLING -> {
                    imageType.setImageResource(R.drawable.ic_group)
                    textType.setText(R.string.pooling_taxi)
                }
                FleetType.TAXI -> {
                    imageType.setImageResource(R.drawable.ic_taxi)
                    textType.setText(R.string.personal_taxi)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
