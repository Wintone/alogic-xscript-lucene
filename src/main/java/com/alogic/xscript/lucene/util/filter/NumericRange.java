package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class NumericRange extends FilterBuilder.Abstract {

	protected Query filter;
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String lowerTerm = PropertiesConstants.getString(p, "lowerTerm", null);
		String upperTerm = PropertiesConstants.getString(p, "upperTerm", null);
		Boolean includeLower = PropertiesConstants.getBoolean(p, "includeLower", true);
		Boolean includeUpper = PropertiesConstants.getBoolean(p, "includeUpper", true);
	    Double lower = lowerTerm == null ? null : Double.parseDouble(lowerTerm);
	    Double upper = upperTerm == null ? null : Double.parseDouble(upperTerm);
		filter = NumericRangeQuery.newDoubleRange(field, lower, upper, includeLower, includeUpper);
	}

}
