package com.mrz.paymentgw.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrz.paymentgw.R
import com.mrz.paymentgw.adapter.PackageAdapter
import com.mrz.paymentgw.database.AppEntityPackageList
import com.mrz.paymentgw.viewmodel.PackageListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageFragment : Fragment() {

    lateinit var viewModel: PackageListViewModel
    lateinit var packageAdapter: PackageAdapter
    private lateinit var packageRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        packageRecyclerView = requireView().findViewById(R.id.package_recycler)

        initPackageRecyclerView()
        initPackageList()
    }

    private fun initPackageList() {
        viewModel = ViewModelProvider(this)[PackageListViewModel::class.java]
        viewModel.getRecordsObserver().observe(viewLifecycleOwner, object : Observer<List<AppEntityPackageList>>{
            override fun onChanged(t: List<AppEntityPackageList>?) {

                //if (t != null) { }

                packageAdapter.setPackageList(t)
                packageAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initPackageRecyclerView() {
        packageAdapter = PackageAdapter()

        packageAdapter.onMovieClick {
            val action = PackageFragmentDirections.actionPackageListToPageDetails(it)
            findNavController().navigate(action)
        }

        packageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        packageRecyclerView.adapter = packageAdapter
        packageAdapter.notifyDataSetChanged()
    }
}