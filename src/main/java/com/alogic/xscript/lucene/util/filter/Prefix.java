package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * PrefixQuery前缀字符查询
 *
 */

public class Prefix extends FilterBuilder.Abstract {
	
	protected Query filter;

	@Override
	public Query getFilter(Properties p) {
		return filter;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String q = PropertiesConstants.getString(p, "q", "");
		filter = new PrefixQuery(new Term(field, q));
	}

}
