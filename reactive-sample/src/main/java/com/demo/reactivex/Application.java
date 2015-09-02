package com.demo.reactivex;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 
 * @author shrey.sharma
 *
 */
public class Application {

	public static void main(String[] args) {
		// First
		first();
		// Second
		second();
		// Third
		third();
		// Fourth
		fourth();
		// Fifth
		fifth();
	}

	/**
	 * basic example for observer and subscriber
	 */
	private static void first() {
		// Observable emits data
		Observable<String> observable = Observable
				.create(new Observable.OnSubscribe<String>() {
					public void call(Subscriber<? super String> sub) {
						sub.onNext("First Example with subscribers");
						sub.onCompleted();
					}
				});

		// Subscriber consumer data emitted by Observable
		Subscriber<String> mySubscriber = new Subscriber<String>() {

			public void onNext(String s) {
				System.out.println(s);
			}

			public void onCompleted() {
			}

			public void onError(Throwable e) {
			}

		};
		// register subscriber to observable
		observable.subscribe(mySubscriber);
	}

	/**
	 * simple example where we do not need any subscriber rather we can work
	 * with actions
	 */
	private static void second() {
		Observable<String> observable = Observable
				.just("Second Example with single actions");
		Action1<String> action = new Action1<String>() {
			public void call(String val) {
				System.out.println(val);
			}
		};
		observable.subscribe(action);
	}

	/**
	 * example with multiple actions
	 */
	private static void third() {
		Observable<String> observable = Observable.just(
				"Third Example with multiple actions", "1", "2");
		Action1<String> onNext = new Action1<String>() {
			public void call(String val) {
				System.out.println(val);
			}
		};

		Action1<Throwable> onError = new Action1<Throwable>() {
			public void call(Throwable val) {
				val.printStackTrace();
			}
		};
		Action0 onComplete = new Action0() {
			public void call() {
				System.out.println("Third() Completed");
			}
		};
		observable.subscribe(onNext, onError, onComplete);
	}

	/**
	 * one liner example of observable using lambda expressions
	 */
	private static void fourth() {
		Observable.just("One Liner Example").subscribe(
				s -> System.out.println(s));
	}

	/**
	 * Introduction with operators
	 */
	private static void fifth() {
		// a
		Observable.just("Fifth Example with operators")
				.map(new Func1<String, Integer>() {

					@Override
					public Integer call(String s) {
						return s.hashCode();
					}
				}).subscribe(s -> System.out.println(s));
		// b, with two maps...we want our subscribers to do less work so map
		// does all processing for us
		Observable.just("Hello, world!").map(s -> s.hashCode())
				.map(i -> Integer.toString(i))
				.subscribe(s -> System.out.println(s));
	}
}
