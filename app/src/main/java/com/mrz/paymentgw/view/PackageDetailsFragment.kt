package com.mrz.paymentgw.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.mrz.paymentgw.R
import com.mrz.paymentgw.databinding.FragmentPackageDetailsBinding
import com.paypal.checkout.approve.Approval
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.*
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
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
                approval ->  approval.orderActions.capture{ captureOrderResult ->  Log.d("aaaaaa", "CaptureOrderResult: $captureOrderResult")}
            },
            onCancel = OnCancel { Log.d("OnCancel", "Buyer canceled the PayPal experience.")
            },
            onError = OnError { errorInfo -> Log.d("OnError", "Error: $errorInfo")
            }
        )

        binding.packageTitle.text = args.packageItem?.title
        binding.packageDetails.text = args.packageItem?.details
        binding.packagePrice.text = resources.getText(R.string.bdt).toString() + args.packageItem?.price.toString()
        binding.packageValidity.text = resources.getText(R.string.validity).toString()+" " + args.packageItem?.validity.toString()+ " Days"
    }
}