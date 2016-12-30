package com.alogic.xscript.lucene.util.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * WildcardQuery(通配符查询)
 *
 */

public class Wildcard extends FilterBuilder.Abstract {
	
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
		if (StringUtils.isEmpty(field)) {
			throw new BaseException("core.no_field", "field is not set,check your script.");
		}
		if (StringUtils.isEmpty(q)) {
			throw new BaseException("core.no_q", "q is not set,check your script.");
		}
		if (StringUtils.isEmpty(occur)) {
			throw new BaseException("core.no_occur", "occur is not set,check your script.");
		}		
		filter = new WildcardQuery(new Term(field, q));
	}

}
