package org.datenklaus.android

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val FRAGMENT_CONNECTING_TAG = "FRAGMENT_CONNECTING"
        const val FRAGMENT_INTRO = "FRAGMENT_INTRO"
    }

    private var loadError: Boolean = false
    private var url = "http://192.168.0.4:8000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            startFragment(IntroFragment.newInstance(), FRAGMENT_INTRO)
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.setAppCacheEnabled(false)
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                val fragment =
                    supportFragmentManager.findFragmentByTag(FRAGMENT_CONNECTING_TAG) as? ConnectingFragment
                if (fragment != null && fragment.isVisible)
                    fragment.setLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (!loadError) {
                    supportFragmentManager.popBackStack()
                    fragment_container.visibility = GONE
                    webView.visibility = VISIBLE
                } else {
                    val fragment =
                        supportFragmentManager.findFragmentByTag(FRAGMENT_CONNECTING_TAG) as? ConnectingFragment
                    if (fragment != null && fragment.isVisible) {
                        fragment.setError()
                        Handler().postDelayed(Runnable {
                            startFragment(
                                IntroFragment.newInstance(),
                                FRAGMENT_INTRO
                            )
                        }, 5000)
                    }
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                loadError = true
                // startFragment(IntroFragment.newInstance(true), FRAGMENT_INTRO)
            }
        }
        //  webView.loadUrl("http://192.168.1.2:8000/")
        /*
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        myWebView.systemUiVisibility =
            SYSTEM_UI_FLAG_IMMERSIVE or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_HIDE_NAVIGATION
        myWebView.setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            myWebView.systemUiVisibility =
                SYSTEM_UI_FLAG_IMMERSIVE or SYSTEM_UI_FLAG_FULLSCREEN or SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
         */
    }

    fun startWebView(url: String?) {
        if (url != null) {
            this.url = url
        }
        startFragment(ConnectingFragment.newInstance(), FRAGMENT_CONNECTING_TAG)
    }

    fun loadUrl() {
        loadError = false
        webView.loadUrl(this.url)
    }

    fun startFragment(fragment: Fragment, tag: String) {
        webView.visibility = GONE
        fragment_container.visibility = VISIBLE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }
}
