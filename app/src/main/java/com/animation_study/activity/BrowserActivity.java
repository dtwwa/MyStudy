package com.animation_study.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.animation_study.custom.MyWebView;
import com.animation_study.R;

/**
 * Created by css on 2017/10/14.
 */

public class BrowserActivity extends Activity implements View.OnClickListener {
    private MyWebView webView = null;
    private long exitTime = 0;
    private ProgressBar ps;
    private LinearLayout ll_top;
    private Button bt_back;
    private Button bt_forwork;
    private Button bt_home;
    private EditText et_input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvivity_browser);
        findView();
    }

    private void findView() {
        webView = (MyWebView) findViewById(R.id.webview);
        ps = (ProgressBar) findViewById(R.id.ps);
        ll_top = (LinearLayout) findViewById(R.id.ll_top);
        et_input = (EditText) findViewById(R.id.et_input);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_forwork = (Button) findViewById(R.id.bt_forword);
        bt_home = (Button) findViewById(R.id.bt_home);
        bt_back.setOnClickListener(this);
        bt_forwork.setOnClickListener(this);
        bt_home.setOnClickListener(this);
        webView.getSettings().setBuiltInZoomControls(false);

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);//????????????viewport
        settings.setLoadWithOverviewMode(true);   //???????????????
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//??????????????????

        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    webView.loadUrl("http://www.baidu.com/s?wd=" + et_input.getText().toString());
                    return true;
                }
                return false;//???????????????????????????????????????
            }
        });


        webView.setOnScrollChangedCallback(new MyWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int top) {
                if (ll_top.getAlpha() == 0) {
                    AnimatorSet as = new AnimatorSet();
                    as.play(ObjectAnimator.ofFloat(ll_top, "translationY", -ll_top.getMeasuredHeight(), 0f).setDuration(500)).with(ObjectAnimator.ofFloat(ll_top, "alpha", 0, 1f).setDuration(500));
                    as.start();
                    as.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ll_top.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onTouch(int dy) {
                if (dy < -80 && ll_top.getAlpha() == 1) {
                    AnimatorSet as = new AnimatorSet();
                    as.play(ObjectAnimator.ofFloat(ll_top, "translationY", 0f, -ll_top.getMeasuredHeight()).setDuration(500)).with(ObjectAnimator.ofFloat(ll_top, "alpha", 1f, 0).setDuration(500));
                    as.start();
                    as.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ll_top.setVisibility(View.GONE);
                        }
                    });
                }

                if (dy >= 80 && ll_top.getAlpha() == 0) {
                    AnimatorSet as = new AnimatorSet();
                    as.play(ObjectAnimator.ofFloat(ll_top, "translationY", -ll_top.getMeasuredHeight(), 0f).setDuration(500)).with(ObjectAnimator.ofFloat(ll_top, "alpha", 0, 1f).setDuration(500));
                    as.start();
                    as.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            ll_top.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            //?????????webView?????????????????????????????????????????????,?????????????????????????????????
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ps.setVisibility(View.VISIBLE);
                ps.setProgress(0);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webView.canGoBack()) {//???????????????????????????????????????,,??????????????????
                    bt_back.setEnabled(true);
                } else {
                    bt_back.setEnabled(false);
                }
                if (webView.canGoForward()) {
                    bt_forwork.setEnabled(true);
                } else {
                    bt_forwork.setEnabled(false);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                et_input.setHint(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    if (ps.getProgress() < newProgress) {//???????????????????????????????????? ????????????
                        ps.setProgress(newProgress);
                    }
                } else {
                    if (ps.getVisibility() == View.GONE) {
                        return;
                    }
                    ps.setProgress(newProgress);
                    Animation tran = new TranslateAnimation(0, 0, 0, -ps.getMeasuredHeight());
                    tran.setDuration(500);
                    Animation alpha = new AlphaAnimation(1.0f, 0.0f);
                    alpha.setDuration(500);
                    AnimationSet as = new AnimationSet(true);
                    as.addAnimation(tran);
                    as.addAnimation(alpha);
                    ps.startAnimation(as);
                    as.setFillBefore(true);
                    as.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ps.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);  //??????WebView??????,????????????js??????
        webView.loadUrl("http://ecard.avceit.cn/systemInit.action");

    }

    //???????????????????????????????????????,??????????????????????????????
    //1.webView.canGoBack()???????????????????????????,?????????goback()
    //2.???????????????????????????????????????App,??????????????????Toast
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "????????????????????????",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                webView.goBack();//?????????????????????????????????????????????????????????????????????
                break;
            case R.id.bt_forword:
                webView.goForward();
                break;
            case R.id.bt_home:
                webView.loadUrl("http://www.baidu.com");
                break;
        }
    }
}
