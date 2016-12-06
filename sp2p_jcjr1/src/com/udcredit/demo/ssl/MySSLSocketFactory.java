package com.udcredit.demo.ssl;

/**
 *
 */

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * SSL全部信任登录
 *
 * @author linys
 *
 */
public class MySSLSocketFactory extends SSLSocketFactory{

    SSLContext sslContext = SSLContext.getInstance("TLS");

    public MySSLSocketFactory(KeyStore truststore)
        throws NoSuchAlgorithmException, KeyManagementException,
        KeyStoreException, UnrecoverableKeyException
    {

        super(truststore);

        TrustManager tm = new X509TrustManager(){

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException
            {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException
            {
            }

            public X509Certificate[] getAcceptedIssuers()
            {
                return null;
            }

        };
        sslContext.init(null, new TrustManager[] { tm }, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port,
                               boolean autoClose) throws IOException, UnknownHostException
    {
        SSLSocket result = (SSLSocket)sslContext.getSocketFactory().createSocket(socket, host, port,
            autoClose);
//      String[] prots = result.getSupportedProtocols();
        result.setEnabledProtocols(new String[]{"TLSv1"});
        return result;
    }

    @Override
    public Socket createSocket() throws IOException
    {
        SSLSocket result = (SSLSocket)sslContext.getSocketFactory().createSocket();
//      String[] prots = result.getSupportedProtocols();
        result.setEnabledProtocols(new String[]{"TLSv1"});
        return result;
    }

}
