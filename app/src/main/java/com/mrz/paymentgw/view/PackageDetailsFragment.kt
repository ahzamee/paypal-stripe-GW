package com.mrz.paymentgw.view

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mrz.paymentgw.R
import com.mrz.paymentgw.databinding.FragmentPackageDetailsBinding
import com.mrz.paymentgw.viewmodel.PackageDetailsViewModel
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.*
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageDetailsFragment : Fragment() {

    lateinit var binding: FragmentPackageDetailsBinding
    private val args: PackageDetailsFragmentArgs by navArgs()
    private val viewModel: PackageDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //selectPaymentMethod()
        binding.onPaymentMethodClicked.setOnCheckedChangeListener{ radioGroup: RadioGroup, checkID: Int ->
            if (checkID == R.id.radio_stripe){
                binding.paymentButtonContainer.visibility = View.GONE
                binding.stripPayBtn.visibility = View.VISIBLE
            }else{
                binding.paymentButtonContainer.visibility = View.VISIBLE
                binding.stripPayBtn.visibility = View.GONE
                startPaypalPayment()
            }
        }

        binding.packageTitle.text = args.packageItem?.title
        binding.packageDetails.text = args.packageItem?.details
        binding.packagePrice.text = resources.getText(R.string.bdt).toString() + args.packageItem?.price.toString()
        binding.packageValidity.text = resources.getText(R.string.validity).toString()+" " + args.packageItem?.validity.toString()+ " Days"

        binding.stripPayBtn.setOnClickListener {

        }
    }

    private fun startPaypalPayment() {
        binding.paymentButtonContainer.setup(
            createOrder = CreateOrder{ CreateOrderActions ->
                val order= Order(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(userAction = UserAction.PAY_NOW),
                    purchaseUnitList =
                    listOf(
                        PurchaseUnit(
                            amount = Amount(
                                currencyCode = CurrencyCode.USD, value = args.packageItem?.price.toString()
                            )
                        )
                    )
                )
                CreateOrderActions.create(order)
            },
            onApprove = OnApprove {
                    approval ->  approval.orderActions.capture{
                    captureOrderResult -> Log.d("aaaaaa", "CaptureOrderResult: $captureOrderResult.")
                    when(captureOrderResult){

                        is CaptureOrderResult.Success -> {
                            viewModel.savePaypalPayment(captureOrderResult.orderResponse!!.purchaseUnits)
                        }
                        is CaptureOrderResult.Error -> {
                            Toast.makeText(requireContext(), "Failed to retrieve payment information", Toast.LENGTH_SHORT).show()
                        }
                    }
                    }
            },
            onCancel = OnCancel { Log.d("OnCancel", "Buyer canceled the PayPal experience.")
            },
            onError = OnError { errorInfo ->
                Log.d("OnError", "Error: $errorInfo")
            }
        )
    }
}