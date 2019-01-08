package reagodjj.example.com.expandablelistview.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    private static final int TIMEOUT_IN_MILLION = 5000;

    public static String doGet(String urlStr) {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(TIMEOUT_IN_MILLION);
            httpURLConnection.setReadTimeout(TIMEOUT_IN_MILLION);
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                int length;
                byte[] buffer = new byte[1024];

                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
}
