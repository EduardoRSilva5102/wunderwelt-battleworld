package com.hexinteractive.wunderwelt.ui.marvel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.*;

/** A bounded image-only client. It never receives the Comic Vine API key. */
final class RemotePortraitLoader {
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS).followRedirects(false).build();
    private Call call;
    void cancel() { if (call != null) call.cancel(); call = null; }
    void load(String url, ImageView target) {
        cancel();
        HttpUrl parsed = HttpUrl.parse(url);
        if (parsed == null || !parsed.isHttps()) return;
        String host = parsed.host();
        if (!(host.equals("comicvine.gamespot.com") || host.endsWith(".cbsistatic.com")
                || host.endsWith(".gamespot.com") || host.endsWith(".giantbomb.com"))) return;
        Call request = CLIENT.newCall(new Request.Builder().url(parsed).build());
        call = request;
        request.enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException failure) { /* keep XML portrait */ }
            @Override public void onResponse(Call call, Response response) throws IOException {
                try (Response closeable = response) {
                    if (!response.isSuccessful() || response.body() == null) return;
                    byte[] bytes = response.body().byteStream().readNBytes(2 * 1024 * 1024 + 1);
                    if (bytes.length > 2 * 1024 * 1024) return;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                    if (options.outWidth <= 0 || options.outHeight <= 0) return;
                    options.inSampleSize = 1;
                    while (Math.max(options.outWidth, options.outHeight) / options.inSampleSize > 800) options.inSampleSize *= 2;
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                    target.post(() -> { if (!request.isCanceled() && bitmap != null) target.setImageBitmap(bitmap); });
                }
            }
        });
    }
}
