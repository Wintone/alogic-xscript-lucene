package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 *TermQuery
 * 单个关键字域查询
 *
 */

public class TermFilter extends FilterBuilder.Abstract {
	
	protected Query filter;
	
	protected String occur = "MUST";
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}
	
	@Override
	public String getOccur() {
		return occur;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String q = PropertiesConstants.getString(p, "q", "");
		occur = PropertiesConstants.getString(p, "occur", "MUST");
		filter = new TermQuery(new Term(field, q));
	}

}
