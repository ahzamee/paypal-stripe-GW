package com.mrz.paymentgw.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mrz.paymentgw.R
import com.mrz.paymentgw.BuildConfig.STAGING_STRIPE_PUBLISH_KEY
import com.mrz.paymentgw.databinding.FragmentPackageDetailsBinding
import com.mrz.paymentgw.util.Status
import com.mrz.paymentgw.viewmodel.PackageDetailsViewModel
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.createorder.*
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.*
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PackageDetailsFragment : Fragment() {

    lateinit var paymentSheet: PaymentSheet
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

        PaymentConfiguration.init(requireContext(), STAGING_STRIPE_PUBLISH_KEY)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

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

            viewModel.getStripeAccessToken(1)

            viewModel.stripeAccessToken.observe(viewLifecycleOwner){
                when(it.getContentIfNotHandled()?.status){

                    Status.LOADING->{
                        Log.d("aaaaaa", "LOADING")
                    }
                    Status.SUCCESS->{
                        Log.d("aaaaaa", it.peekContent().data!!.clientSecret)
                        startStripePayment(it.peekContent().data!!.clientSecret)
                    }
                    Status.ERROR->{
                        Log.d("aaaaaa", "error")
                    }

                    else -> TODO()
                }
            }
        }
    }

    private fun startStripePayment(clientSecret: String) {




        paymentSheet.presentWithPaymentIntent(
            clientSecret
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult){
        when(paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                Log.d("aaaaa","Canceled")
            }
            is PaymentSheetResult.Failed -> {
                Log.d("aaaaaError:","Failed: ${paymentSheetResult.error}")
            }
            is PaymentSheetResult.Completed -> {
                // Display for example, an order confirmation screen
                Log.d("aaaaa","Completed")
            }
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
                            //viewModel.savePaypalPayment(captureOrderResult.orderResponse!!.purchaseUnits)
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