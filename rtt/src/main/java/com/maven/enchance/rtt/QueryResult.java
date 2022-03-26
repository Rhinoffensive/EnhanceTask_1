package com.maven.enchance.rtt;

import java.util.*;




public class QueryResult {

	public static <T> Set<T> mergeSet(final Set<T> a, final Set<T> b) {

		// Adding all elements of respective Sets
		// using addAll() method
		return new HashSet<T>() {
			{
				addAll(a);
				addAll(b);
			}
		};
	}
	

	public String URLLink;
	public List<Query> queryHit = new ArrayList<Query>();
	//public ArrayList<int[]> Odometer = new ArrayList<int[]>();
	//public ArrayList<int[]> Seat = new ArrayList<int[]>();
	//public String Type;

	public QueryResult(String urlLink, Query q) {
		this.URLLink = urlLink;
		this.queryHit.add(q);
		

	}

//	public QueryResult Add(QueryResult qr) {
//		if (this.URLLink == qr.URLLink) {
//			Set<int[]> a = new HashSet<int[]>();
//			a.addAll(this.Odometer);
//			Set<int[]> b = new HashSet<int[]>();
//			b.addAll(qr.Odometer);
//			
//			ArrayList<int[]> mergeSet = (ArrayList<int[]>) mergeSet(a,b);
//			this.Odometer = mergeSet;
//
//		}
//		return this;
//
//	}

}
