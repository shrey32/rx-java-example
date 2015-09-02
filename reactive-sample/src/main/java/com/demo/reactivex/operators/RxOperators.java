package com.demo.reactivex.operators;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rx.Observable;

/**
 * 
 * @author shrey.sharma
 *
 */
public class RxOperators implements Query {

	/**
	 * 1) With flatMap(), solving this problem is easy; after splitting the list
	 * of URLs into individual items, I can use getTitle() in flatMap() for each
	 * url before it reaches the Subscriber:
	 */
	/**
	 * 2) filter() emits the same item it received, but only if it passes the
	 * boolean check.
	 */
	/**
	 * 3) take() emits, at most, the number of items specified. (If there are
	 * fewer than 5 titles it'll just stop early.)
	 */
	/**
	 * 4) doOnNext() allows us to add extra behavior each time an item is
	 * emitted, in this case saving the title.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(RxOperators.class);

	public static void main(String[] args) {
		RxOperators rxOp = new RxOperators();
		rxOp.query("Hello, world!").flatMap(data -> Observable.from(data))
				.flatMap(url -> rxOp.getTitle(url))
				.filter(title -> title != null).take(2)
				.doOnNext(title -> rxOp.saveTitle(title))
				.subscribe(title -> logger.info(title));
	}

	@Override
	public Observable<List<Data>> query(String text) {
		List<Data> val = new ArrayList<Data>();
		val.add(new Data("Url 1", "Title 1"));
		val.add(new Data("Url 2", "Title 2"));
		val.add(new Data("Url 3", "Title 3"));
		val.add(new Data("Url 4", null));
		Observable<List<Data>> query = Observable.just(val);
		return query;
	}

	@Override
	public Observable<String> getTitle(Data data) {
		return Observable.just(data.title);
	}

	public static class Data {
		public String url, title;

		public Data(String url, String title) {
			this.url = url;
			this.title = title;
		}
	}

	@Override
	public void saveTitle(String title) {
		logger.info(title + " Saved Successfully");
	}

}
