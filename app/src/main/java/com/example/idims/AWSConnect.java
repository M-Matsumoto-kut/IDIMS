package com.example.idims;

import android.os.AsyncTask;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class AWSConnect extends AsyncTask {
    private AWSConnect.CallBackTask mcallbacktask;
    @NotNull
    public static final AWSConnect.Companion Companion = new AWSConnect.Companion((DefaultConstructorMarker)null);

    protected void onPreExecute() {
    }

    @NotNull
    protected String doInBackground(@NotNull String... params) {
        Intrinsics.checkNotNullParameter(params, "params");
        return Companion.execute1(params[0], params[1]);
    }

    // $FF: synthetic method
    // $FF: bridge method
    public Object doInBackground(Object[] var1) {
        return this.doInBackground((String[])var1);
    }

    protected void onPostExecute(@NotNull String result) {
        Intrinsics.checkNotNullParameter(result, "result");
        AWSConnect.CallBackTask var10000 = this.mcallbacktask;
        if (var10000 != null) {
            var10000.CallBack(result);
        }

    }

    // $FF: synthetic method
    // $FF: bridge method
    public void onPostExecute(Object var1) {
        this.onPostExecute((String)var1);
    }

    public final void setOnCallBack(@NotNull AWSConnect.CallBackTask t_object) {
        Intrinsics.checkNotNullParameter(t_object, "t_object");
        this.mcallbacktask = t_object;
    }

    @Metadata(
            mv = {1, 6, 0},
            k = 1,
            xi = 2,
            d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"},
            d2 = {"Lcom/example/support_kuttcg/AWSconnect$CallBackTask;", "", "CallBack", "", "result", "", "app_debug"}
    )
    public interface CallBackTask {
        void CallBack(@NotNull String var1);
    }

    @Metadata(
            mv = {1, 6, 0},
            k = 1,
            xi = 2,
            d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004¨\u0006\n"},
            d2 = {"Lcom/example/support_kuttcg/AWSconnect$Companion;", "", "()V", "convertToString", "", "stream", "Ljava/io/InputStream;", "execute1", "argStrApiUrl", "formParam", "app_debug"}
    )
    public static final class Companion {
        @NotNull
        public final String execute1(@NotNull String argStrApiUrl, @NotNull String formParam) {
            Intrinsics.checkNotNullParameter(argStrApiUrl, "argStrApiUrl");
            Intrinsics.checkNotNullParameter(formParam, "formParam");
            String ret = "";
            HttpURLConnection urlConnection = (HttpURLConnection)null;

            try {
                URL url = new URL(argStrApiUrl);
                URLConnection var10000 = url.openConnection();
                if (var10000 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
                }

                urlConnection = (HttpURLConnection)var10000;
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setRequestProperty("User-Agent", "Android");
                urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
                urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(formParam);
                out.close();
                int responseCode = urlConnection.getResponseCode();
                InputStream stream = urlConnection.getInputStream();
                AWSConnect.Companion var13 = (AWSConnect.Companion)this;
                Intrinsics.checkNotNullExpressionValue(stream, "stream");
                ret = var13.convertToString(stream);
                Log.d("execute", "URL:" + argStrApiUrl);
                Log.d("execute", "HttpStatusCode:" + responseCode);
                Log.d("execute", "ResponseData:" + ret);
            } catch (IOException var11) {
                var11.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }

            return ret;
        }

        @NotNull
        public final String convertToString(@NotNull InputStream stream) throws IOException {
            Intrinsics.checkNotNullParameter(stream, "stream");
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader((Reader)(new InputStreamReader(stream, "UTF-8")));

            for(String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }

            try {
                stream.close();
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            String var10000 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(var10000, "sb.toString()");
            return var10000;
        }

        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
