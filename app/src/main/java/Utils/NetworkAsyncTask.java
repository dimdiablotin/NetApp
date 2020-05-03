package Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by dimel on 01/05/2020.
 */
public class NetworkAsyncTask extends AsyncTask<String, Void, String> {

    public interface Listeners {
        void onPreExecute();

        void doInBackground();

        void onPostExecute(String success);
    }

    private WeakReference<Listeners> callback;

    public NetworkAsyncTask(Listeners callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute();
        Log.e("TAG", "AsyncTask is started");
    }

    @Override
    protected String doInBackground(String... url) {
        this.callback.get().doInBackground();
        Log.e("TAG", "AsyncTask doing some big work...");
        return MyHttpURLConnection.startHttpRequest(url[0]);
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success);
        Log.e("TAG", "AsyncTask is finished");
    }
}
