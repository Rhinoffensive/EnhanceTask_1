package com.maven.enchance.rtt;

import java.util.*;

public class QueryResult {

	public String URLLink;
	public List<Query> query;

	public QueryResult(String urlLink, Query q) {
		System.out.println("Query result!!");
		String[] url = urlLink.split("\\?",0);
		this.URLLink = url[0];	
		System.out.println(this.URLLink);
		query = new ArrayList<Query>();
		query.add(q);
		
		
	}

	public void AddQuery(Query q) {
		query.add(q);

	}

}
