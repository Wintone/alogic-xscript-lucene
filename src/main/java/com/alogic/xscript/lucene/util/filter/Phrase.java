package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;

public class Phrase extends FilterBuilder.Abstract {

	protected Query filter;
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}

	@Override
	public void configure(Properties p) {
		// TODO Auto-generated method stub
		
	}

}
