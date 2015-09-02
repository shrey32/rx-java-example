package com.demo.reactivex.operators;

import java.util.List;

import rx.Observable;

import com.demo.reactivex.operators.RxOperators.Data;

/**
 * 
 * @author shrey.sharma
 *
 */
public interface Query {
	// Returns a List of website URLs based on a text search
	Observable<List<Data>> query(String text);

	// Returns the title of a website, or null if 404
	Observable<String> getTitle(Data URL);

	void saveTitle(String title);

}
