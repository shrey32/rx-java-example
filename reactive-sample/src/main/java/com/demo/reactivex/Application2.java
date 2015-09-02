package com.demo.reactivex;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import rx.Observable;
import rx.Subscriber;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Reactive programming example
 * 
 * @author shrey.sharma
 *
 */
public class Application2 {

	public static void main(String[] args) {
		Observable.just("https://api.github.com/users").subscribe(
				url -> fetch(url));
	}

	/**
	 * fetches data from github server
	 * 
	 * @param url
	 *            to get all users
	 */
	private static void fetch(String url) {
		Observable<String> requestStream = Observable
				.create(new Observable.OnSubscribe<String>() {
					@Override
					public void call(Subscriber<? super String> sub) {
						try {
							OkHttpClient client = new OkHttpClient();
							Request request = new Request.Builder().url(url)
									.build();
							Response response = client.newCall(request)
									.execute();
							sub.onNext(response.body().string());
							sub.onCompleted();
						} catch (Exception e) {
							sub.onError(e);
						}
					}
				});

		requestStream.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("List Displayed");
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(String jsonResponse) {
				parse(jsonResponse)
						.flatMap(followData -> Observable.from(followData))
						.flatMap(followData -> display(followData)).limit(3)
						.subscribe();
			}
		});
	}

	/**
	 * parse data out from reponse string
	 * 
	 * @param jsonStr
	 * @return Observable<List<FollowData>>
	 */
	private static Observable<List<FollowData>> parse(String jsonStr) {
		try {
			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();
			// convert json string to object
			List<FollowData> dataList = Arrays.asList(objectMapper.readValue(
					jsonStr, FollowData[].class));
			return Observable.just(dataList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * display on console
	 * 
	 * @param followData
	 * @return Observable<FollowData>
	 */
	private static Observable<FollowData> display(FollowData followData) {
		System.out.println(followData.id);
		System.out.println(followData.login);
		System.out.println("Image " + followData.avatar_url);
		System.out.println("-----------------------------------------------");
		return Observable.just(followData);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class FollowData implements Serializable {
		private static final long serialVersionUID = 1L;
		public String avatar_url, login;
		public int id;
	}
}
