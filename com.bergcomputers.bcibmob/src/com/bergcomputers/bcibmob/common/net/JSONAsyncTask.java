package com.bergcomputers.bcibmob.common.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.bergcomputers.bcibmob.common.Util;

public class JSONAsyncTask extends AsyncTask<JSONObject, Void, String> {

	private String url;
	private IJSONNetworkActivity activity;
	private int currentAction;

	public JSONAsyncTask(String pURL, IJSONNetworkActivity pActivity, int currentAction) {
		super();
		this.url = pURL;
		this.activity = pActivity;
		this.currentAction = currentAction;
	}

	/**
	 * The system calls this to perform work in a worker thread and delivers it
	 * the parameters given to AsyncTask.execute()
	 */
	protected String doInBackground(JSONObject... params) {
		// load the survey
		String jsonString = loadJSONFromNetwork(null);
		return jsonString;
	}

	/**
	 * @param params
	 */
	private String loadJSONFromNetwork(JSONObject params) {
		HttpClient httpClient = HTTPHelper.createHTTPClient();
		//HttpPost httppost = new HttpPost(url);
		HttpGet httpget = new HttpGet(url);

		String jsonString = null;
		try {
			//String encryptedParams = params.toString();

			/*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			// grouped params
			nameValuePairs.add(new BasicNameValuePair(Util.PARAM_DATAEN, encryptedParams));
			// model == android
			nameValuePairs.add(new BasicNameValuePair(Util.PARAM_MODEL, Util.PARAM_MODEL_VALUE));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/

			// Execute HTTP Post Request
			HttpResponse response = httpClient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				jsonString = Util.inputStreamToString(response.getEntity().getContent());
				Log.d("loadJSONFromNetwork", jsonString);
				try{
					new JSONObject(jsonString);
				}catch(Exception ex){
					new JSONArray(jsonString);
				}

			} else {
//System.out.println("Error: "+response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
				jsonString = Util.inputStreamToString(response.getEntity().getContent());
				Log.d("loadJSONFromNetwork", "Error: "+response.getStatusLine().getStatusCode() + " : " + response.getStatusLine().getReasonPhrase());
				Log.d("loadJSONFromNetwork", jsonString);
				new JSONObject(jsonString);
			}
		} catch (ClientProtocolException e) {
			Log.e("get meeting", "https get meeting", e);
		} catch (IOException e) {
			Log.e("get meeting", "https get meeting", e);
		} catch (JSONException e) {
			Log.e("get meeting", "https get meeting", e);
		}
		return jsonString;
	}

	/**
	 * The system calls this to perform work in the UI thread and delivers the
	 * result from doInBackground()
	 */
	protected void onPostExecute(String result) {
		activity.handleResult(result, currentAction);
	}
}
