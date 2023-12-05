package com.kauproject.kausanhak.presentation

import android.app.Activity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesResponseListener
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.consumePurchase
import com.android.billingclient.api.queryPurchasesAsync
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PurchaseHelper(val activity: Activity){
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.OK
                && purchases != null
            ) {
                for (purchase in purchases) {
                    completePurchase(purchase)
                }
            } else if (billingResult.responseCode ==
                BillingClient.BillingResponseCode.USER_CANCELED
            ) {
                _statusText.value = "구매 취소"
            } else {
                _statusText.value = "구매 에러"
            }
        }

    private val purchasesListener =
        PurchasesResponseListener { billingResult, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                _buyEnabled.value = false
                _consumeEnabled.value = true
                _statusText.value = "Previous Purchase Found"
            } else {
                _buyEnabled.value = true
                _consumeEnabled.value = false
            }
        }

    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: ProductDetails
    private lateinit var purchase: Purchase

    private val productId = "post_1"

    private val _productName = MutableStateFlow("검색중..")
    val productName = _productName.asStateFlow()

    private val _buyEnabled = MutableStateFlow(false)
    val buyEnabled = _buyEnabled.asStateFlow()

    private val _consumeEnabled = MutableStateFlow(false)
    val consumeEnabled = _consumeEnabled.asStateFlow()

    private val _statusText = MutableStateFlow("초기화중..")
    val statusText = _statusText.asStateFlow()

    fun billingSetup(){
        billingClient = BillingClient.newBuilder(activity)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        billingClient.startConnection(object : BillingClientStateListener{
            override fun onBillingServiceDisconnected() {
                _statusText.value = "Billing Client Connection Lost"
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                    _statusText.value = "Billing Client Connected"
                    queryProduct(productId = productId)
                    reloadPurchase()
                }else{
                    _statusText.value = "Billing Client Connection Failure"
                }
            }

        })
    }

    fun queryProduct(productId: String){
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(
                            BillingClient.ProductType.INAPP
                        )
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(
            queryProductDetailsParams
        ) { billingResult, productDetailsList ->
            if (productDetailsList.isNotEmpty()) {
                productDetails = productDetailsList[0]
                _productName.value = "Product: " + productDetails.name
            } else {
                _statusText.value = "No Matching Products Found"
                _buyEnabled.value = false
            }
        }
    }

    private fun completePurchase(item: Purchase){
        purchase = item
        if(purchase.purchaseState == Purchase.PurchaseState.PURCHASED){
            _buyEnabled.value = false
            _consumeEnabled.value = true
            _statusText.value = "구매완료"
        }
    }

    fun makePurchase(){
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                ImmutableList.of(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .build()
                )
            )
            .build()
        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    fun consumePurchase(){
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        coroutineScope.launch {
            val result = billingClient.consumePurchase(consumeParams)

            if(result.billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                _statusText.value = "Purchase Consumed"
                _buyEnabled.value = true
                _consumeEnabled.value = false
            }
        }
    }

    private fun reloadPurchase(){
        val queryPurchaseParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.queryPurchasesAsync(
            queryPurchaseParams,
            purchasesListener
        )
    }
}
