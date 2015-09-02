package com.demo.reactivex.errorhandling;

import rx.Observable;
import rx.Subscriber;

/**
 * 
 * @author shrey.sharma
 *
 */
public class RxErrorHandling {

	public static void main(String[] args) {
		String val="Test Error Handling With RxJava";
		Observable.just(val).map(s -> potentialException(s))
				.map(s -> anotherPotentialException(s))
				.subscribe(new Subscriber<String>() {

					@Override
					public void onCompleted() {
						System.out.println("Completed");
					}

					@Override
					public void onError(Throwable e) {
						e.printStackTrace();
					}

					@Override
					public void onNext(String t) {
						System.out.println(t);
					}
				});

	}

	private static String potentialException(String s) {
		System.out.println("Length "+s.length());
		return s;
	}

	private static String anotherPotentialException(String s) {
		System.out.println("And");
		return s;
	}

}
