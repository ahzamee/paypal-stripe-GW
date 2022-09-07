package com.mrz.paymentgw.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mrz.paymentgw.R
import com.mrz.paymentgw.databinding.FragmentPackageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageDetailsFragment : Fragment() {

    lateinit var binding: FragmentPackageDetailsBinding
    private val args: PackageDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.packageTitle.text = args.packageItem?.title
        binding.packageDetails.text = args.packageItem?.details
        binding.packagePrice.text = resources.getText(R.string.bdt).toString() + args.packageItem?.price.toString()
        binding.packageValidity.text = resources.getText(R.string.validity).toString()+" " + args.packageItem?.validity.toString()+ " Days"

        binding.payNowBtn.setOnClickListener {

        }
    }

}