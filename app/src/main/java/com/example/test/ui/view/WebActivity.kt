package com.example.test.ui.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R

class WebActivity : AppCompatActivity() {

    companion object {
        const val intentKeyUrl = "intentKeyUrl"
        const val intentKeyTitle = "intentKeyTitle"
    }

    private lateinit var webView: WebView
    private lateinit var webViewTitle: TextView
    private lateinit var webViewBack: View // 你可能需要根据实际视图组件类型修改此行

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        // 初始化视图组件
        webView = findViewById(R.id.webView)
        webViewTitle = findViewById(R.id.webViewTitle)
        webViewBack = findViewById(R.id.webViewBack)

        initViewData()
    }

    override fun onStop() {
        super.onStop()
        webView.settings.javaScriptEnabled = false
    }

    private fun initViewData() {
        val loadUrl = intent.getStringExtra(intentKeyUrl)
        var webTitle = intent.getStringExtra(intentKeyTitle)

        webTitle = if ((webTitle?.length ?: 0) >= 20) {
            webTitle?.substring(0, 20) + "..."
        } else {
            webTitle
        }

        // 如果使用的是 Toolbar 或其他标题组件，需要在此处设置标题
        supportActionBar?.title = webTitle
        webViewTitle.text = webTitle
        webView.loadUrl(loadUrl ?: "")

        webViewBack.setOnClickListener { finish() }

        webView.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true

            setSupportZoom(true)
            setBuiltInZoomControls(true)
            setDisplayZoomControls(false)

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            setAllowFileAccess(true)
            setJavaScriptCanOpenWindowsAutomatically(true)
            setLoadsImagesAutomatically(true)
            defaultTextEncodingName = "utf-8"
        }
        webView.settings.userAgentString = "Mozilla/5.0 (Linux; Android 10; Mobile; rv:68.0) Gecko/68.0 Firefox/68.0"
        webView.settings.domStorageEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d("WebActivity", "网页内url变化：${url}")
                // 确保 WebView 自己处理所有 http/https 链接
                if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                    view?.loadUrl(url)  // 加载点击的链接
                    return false  // 让 WebView 处理跳转
                }

                if (url != null && url.startsWith("jianshu://")) {
                    // 处理自定义 Scheme
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        // 当没有处理该 Scheme 的应用时的处理逻辑
                        Toast.makeText(this@WebActivity, "未找到处理该链接的应用", Toast.LENGTH_SHORT).show()
                    }
                    return true
                }

                view?.loadUrl(url ?: "")
                return true
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
