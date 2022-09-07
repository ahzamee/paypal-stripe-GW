package com.mrz.paymentgw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrz.paymentgw.R
import com.mrz.paymentgw.database.AppEntityPackageList

class PackageAdapter (): RecyclerView.Adapter<PackageAdapter.PackageViewHolder>() {

        private lateinit var appEntityList: List<AppEntityPackageList>

        var onCLick: ((AppEntityPackageList) -> Unit)? = null

        fun setPackageList(AppEntityList: List<AppEntityPackageList>?) {
            this.appEntityList = AppEntityList!!
        }

        override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
            appEntityList.let { holder.bind(it[position]) }
        }

        override fun getItemCount(): Int {
            return appEntityList.size
        }

        fun onMovieClick(listener: (AppEntityPackageList) -> Unit) {
            onCLick = listener
        }

        inner class PackageViewHolder (view: View): RecyclerView.ViewHolder(view){

            private val itemPackageTitleTxtView: TextView = view.findViewById(R.id.package_title)
            private val itemPackagePriceTxtView: TextView = view.findViewById(R.id.package_price)
            private val itemPackageValidityTextView: TextView = view.findViewById(R.id.package_date)

            fun bind(data: AppEntityPackageList){
                val packagePrice = "BDT " + data.price.toString()
                val packageValidity = "Validity: " + data.validity.toString() + " Days"

                itemPackageTitleTxtView.text = data.title
                itemPackagePriceTxtView.text = packagePrice
                itemPackageValidityTextView.text = packageValidity

                itemView.setOnClickListener {
                    onCLick?.let {
                        it(data)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)

            return PackageViewHolder(view)
    }
}