package com.example.taskbysmartobject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.taskbysmartobject.databinding.ActivityMainBinding
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adLoader: AdLoader
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var adRequest:AdRequest


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)
        adRequest = AdRequest.Builder().build()

//...............Banner AD...............
        binding.adView.loadAd(adRequest)

//................Native AD...............
        adLoader = AdLoader.Builder(this, getString(R.string.native_ad_id))
            .forNativeAd( NativeAd.OnNativeAdLoadedListener { nativeAd ->
                val styles = NativeTemplateStyle.Builder().build()
                binding.myNativeTemplate.setStyles(styles)
                binding.myNativeTemplate.setNativeAd(nativeAd)
            })
            .build()

        adLoader.loadAd(adRequest)

        binding.firstbtn.setOnClickListener {
            startActivity(Intent(this,RecyclerActivity::class.java))
        }

        binding.secondbtn.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this@MainActivity)
            } else {
                loadInterstitialAd()
                binding.secondbtn.isClickable=false
            }
        }
    }
    private fun loadInterstitialAd() {
        InterstitialAd.load(this,getString(R.string.inter_ad_unit_id),adRequest,object :
            InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)

                binding.secondbtn.isClickable=true
                Log.d("waqar", "onAdFailedToLoad: ${adError.responseInfo}")
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)

                binding.secondbtn.isClickable=true
                Log.d("waqar", "onAdLoaded: ${interstitialAd.adUnitId}")
                mInterstitialAd=interstitialAd
                mInterstitialAd?.show(this@MainActivity)
                /*mInterstitialAd!!.fullScreenContentCallback=object :  FullScreenContentCallback(){

                    override fun onAdClicked() {
                        super.onAdClicked()
                        mInterstitialAd=null

                    }

                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        Log.d("waqar", " onAdDismissedFullScreenContent")
                        mInterstitialAd=null

                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        Log.d("waqar", "onAdFailedToShowFullScreenContent: ${p0.message}")
                        mInterstitialAd=null

                    }

                    override fun onAdImpression() {
                        super.onAdImpression()
                        Log.d("waqar", "onAdImpression")
                        mInterstitialAd=null

                    }

                    override fun onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent()
                        Log.d("waqar", "onAdShowedFullScreenContent")
                        mInterstitialAd=null

                    }
                }*/

            }
        })
    }

    override fun onPause() {
        super.onPause()
        Log.d("waqar", "onPause: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("waqar", "onResume: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("waqar", "onRestart: ")
        binding.textView2.text="اردو"
    }

}