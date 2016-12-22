package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * WildcardQuery(通配符查询)
 *
 */

public class Wildcard extends FilterBuilder.Abstract {
	
	protected Query filter;

	@Override
	public Query getFilter(Properties p) {
		return filter;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String q = PropertiesConstants.getString(p, "q", "");
		filter = new WildcardQuery(new Term(field, q));
	}

}
