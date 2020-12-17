package com.rightcode.unite.network;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.rightcode.unite.ApiEnums;
import com.rightcode.unite.Features;
import com.rightcode.unite.Util.Log;
import com.rightcode.unite.Util.NetworkUtil;
import com.rightcode.unite.Util.PreferenceUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import rx.functions.Func1;


abstract public class AbstractNetworkManager {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    protected Context context;

    //----------------------------------------------------------------------------------------------
    // abstract
    //----------------------------------------------------------------------------------------------
    protected abstract String getQaDomain();

    protected abstract String getRealDomain();

    protected abstract void callApiCreateFunc1(Func1<Class<?>, Object> func1);

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    AbstractNetworkManager(@NonNull Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();

            build();
        }
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------
    public void destroy() {
    }

    //----------------------------------------------------------------------------------------------
    // public method
    //----------------------------------------------------------------------------------------------
    public void build() {
        ApiEnums.Country country = LocaleServiceManager.getInstance(context).getServiceCountry();
        String baseUrl = genBaseUrl(country);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .client(buildClient(country))
                .build();


        callApiCreateFunc1(aClass -> retrofit.create(aClass));
        Log.d(String.format("++ build( baseUrl : %s )", baseUrl));
    }

    //----------------------------------------------------------------------------------------------
    // protected method
    //----------------------------------------------------------------------------------------------
    protected String genDomain(ApiEnums.Country country) {

        switch (Features.getServer(context)) {
            case QA:
                return getQaDomain();
            default:
                return getRealDomain();
        }
    }

    //----------------------------------------------------------------------------------------------
    // private method
    //----------------------------------------------------------------------------------------------
    private OkHttpClient buildClient(ApiEnums.Country country) {
        //OkHttpClient.Builder builder = genOkHttpClient(country);         //SSL 와일드 카드 인증서가 아닌 다른 부분때문에 충돌나는 부분 수정
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.sslSocketFactory(getSSLSocketFactory(), new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        });
        builder.hostnameVerifier((hostname, session) -> true);
        NetworkUtil.setTimeout(builder);

        //SSL 와일드 카드 인증서가 아닌 다른 부분때문에 충돌나는 부분 수정
//        builder.addInterceptor(chain -> {
//            Response response = chain.proceed(chain.request());
//            return response;
//        });
        builder.addInterceptor(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.addHeader("rightCode-platform", "Android");
            requestBuilder.addHeader("rightCode-model", Build.MODEL);
            requestBuilder.addHeader("rightCode-os-version", Build.VERSION.RELEASE);
            requestBuilder.addHeader("rightCode-language", Locale.getDefault().getLanguage().toLowerCase());
            if (context != null) {
                try {
                    String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                    requestBuilder.addHeader("rightCode-version", versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String serviceToken = PreferenceUtil.getInstance(context).get(PreferenceUtil.PreferenceKey.ServiceToken, "");
                if (!TextUtils.isEmpty(serviceToken)) {
                    requestBuilder.addHeader("authorization", "bearer " + serviceToken);
                    Log.d("bearer " + serviceToken);
                }
                ApiEnums.Country serviceCountry = LocaleServiceManager.getInstance(context).getServiceCountry();
                if (serviceCountry != null) {
                    requestBuilder.addHeader("rightCode-country", serviceCountry.name());
                }
            }
            return chain.proceed(requestBuilder.build());
        });
        if (Features.TEST_ONLY && Features.SHOW_NETWORK_LOG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    private String genBaseUrl(ApiEnums.Country country) {
        return genDomain(country);
//        String apiVer = PreferenceUtil.getInstance(context).get(PreferenceUtil.PreferenceKey.ApiVersion, null);
//        return genDomain(country) + (TextUtils.isEmpty(apiVer) ? API_VER : "/" + apiVer + "/");

    }

    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return sslSocketFactory;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            return null;
        }
    }
}