package com.bergcomputers.bcibmob.common.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HTTPHelper {
	private static final int TIMEOUT_CONNECTION = 60000; // 60 sec in millis
	private static final int TIMEOUT_SOCKET = 60000; // 60 sec in millis

	public static HttpClient createHTTPClient() {
		HttpParams httpParameters = new BasicHttpParams();

		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT_CONNECTION);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT_SOCKET);
		ConnManagerParams.setMaxTotalConnections(httpParameters, 10);
//	    HttpProtocolParams.setVersion(httpParameters, HttpVersion.HTTP_1_1);
//	    HttpProtocolParams.setContentCharset(httpParameters, HTTP.DEFAULT_CONTENT_CHARSET);
//	    HttpProtocolParams.setUseExpectContinue(httpParameters, true);

	    SchemeRegistry schReg = new SchemeRegistry();
	    schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
	    HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
	    schReg.register(new Scheme("https", socketFactory, 443));
	    ClientConnectionManager conMgr = new ThreadSafeClientConnManager(httpParameters, schReg);

        // Set verifier
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

	    return new DefaultHttpClient(conMgr, httpParameters);
	}

}
