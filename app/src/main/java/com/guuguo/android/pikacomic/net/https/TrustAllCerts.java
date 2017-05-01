package com.guuguo.android.pikacomic.net.https;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * guode 创造于 2017-05-01.
 * 项目 pika
 */
public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }


}    