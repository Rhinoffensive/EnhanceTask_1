package com.maven.enchance.rtt;

import java.util.*;

public class QueryResult {

	public String URLLink;
	public List<Query> query;

	public QueryResult(String urlLink, Query q) {
		this.URLLink = urlLink;	
		query = new ArrayList<Query>();
		query.add(q);
		
		
	}

	public void AddQuery(Query q) {
		query.add(q);

	}

}
