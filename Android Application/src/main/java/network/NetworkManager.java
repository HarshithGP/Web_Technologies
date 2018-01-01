package com.harshith.hw9.network;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yash on 25-11-2016.
 */

public class NetworkManager {
	private static volatile NetworkManager instance;

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

	private static Retrofit.Builder builder;

	private NetworkManager() {
	}

	public static NetworkManager getInstance() {
		if (instance == null) {
			synchronized (NetworkManager.class) {
				if (instance == null) {
					instance = new NetworkManager();
					HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
					logging.setLevel(HttpLoggingInterceptor.Level.BODY);
					httpClient.addInterceptor(logging);
				}
			}
		}
		return instance;
	}

	public <S> S createService(Class<S> serviceClass, String baseURL) {
		httpClient.addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
				Request original = chain.request();
				Request.Builder requestBuilder = original.newBuilder()
						.header("Accept", "application/json")
						.method(original.method(), original.body());
				Request request = requestBuilder.build();
				return chain.proceed(request);
			}
		});
		builder = new Retrofit.Builder()
				.baseUrl(baseURL)
				.addConverterFactory(GsonConverterFactory.create());

		httpClient.connectTimeout(200, TimeUnit.SECONDS);
		httpClient.readTimeout(200, TimeUnit.SECONDS);
		httpClient.hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});

		OkHttpClient client = httpClient.build();
		Retrofit retrofit = builder.client(client).build();
		return retrofit.create(serviceClass);
	}
}
