package com.example.thesis.facebook;

import android.content.Context;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class AsyncFacebookRunner {
	Facebook fb;

	public AsyncFacebookRunner(Facebook fb) {
		this.fb = fb;
	}

	public void logout(final Context context, final RequestListener listener,
			final Object state) {
		new Thread() {
			public void run() {
				try {
					String response = AsyncFacebookRunner.this.fb
							.logout(context);
					if ((response.length() == 0) || (response.equals("false"))) {
						listener.onFacebookError(new FacebookError(
								"auth.expireSession failed"), state);
						return;
					}
					listener.onComplete(response, state);
				} catch (FileNotFoundException e) {
					listener.onFileNotFoundException(e, state);
				} catch (MalformedURLException e) {
					listener.onMalformedURLException(e, state);
				} catch (IOException e) {
					listener.onIOException(e, state);
				}
			}
		}.start();
	}

	public void logout(Context context, RequestListener listener) {
		logout(context, listener, null);
	}

	public void request(Bundle parameters, RequestListener listener,
			Object state) {
		request(null, parameters, "GET", listener, state);
	}

	public void request(Bundle parameters, RequestListener listener) {
		request(null, parameters, "GET", listener, null);
	}

	public void request(String graphPath, RequestListener listener, Object state) {
		request(graphPath, new Bundle(), "GET", listener, state);
	}

	public void request(String graphPath, RequestListener listener) {
		request(graphPath, new Bundle(), "GET", listener, null);
	}

	public void request(String graphPath, Bundle parameters,
			RequestListener listener, Object state) {
		request(graphPath, parameters, "GET", listener, state);
	}

	public void request(String graphPath, Bundle parameters,
			RequestListener listener) {
		request(graphPath, parameters, "GET", listener, null);
	}

	public void request(final String graphPath, final Bundle parameters,
			final String httpMethod, final RequestListener listener,
			final Object state) {
		new Thread() {
			public void run() {
				try {
					String resp = AsyncFacebookRunner.this.fb.request(
							graphPath, parameters, httpMethod);
					listener.onComplete(resp, state);
				} catch (FileNotFoundException e) {
					listener.onFileNotFoundException(e, state);
				} catch (MalformedURLException e) {
					listener.onMalformedURLException(e, state);
				} catch (IOException e) {
					listener.onIOException(e, state);
				}
			}
		}.start();
	}

	public static abstract interface RequestListener {
		public abstract void onComplete(String paramString, Object paramObject);

		public abstract void onIOException(IOException paramIOException,
				Object paramObject);

		public abstract void onFileNotFoundException(
				FileNotFoundException paramFileNotFoundException,
				Object paramObject);

		public abstract void onMalformedURLException(
				MalformedURLException paramMalformedURLException,
				Object paramObject);

		public abstract void onFacebookError(FacebookError paramFacebookError,
				Object paramObject);
	}
}
