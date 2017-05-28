package com.guuguo.android.pikacomic.net.https

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Created by Yangmu on 17/1/19.
 */

class TrustAllCerts : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }

    class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }

    companion object {

        fun createSSLSocketFactory(): SSLSocketFactory? {
            var ssfFactory: SSLSocketFactory? = null

            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())

                ssfFactory = sc.socketFactory
            } catch (e: Exception) {
            }

            return ssfFactory
        }
    }
}