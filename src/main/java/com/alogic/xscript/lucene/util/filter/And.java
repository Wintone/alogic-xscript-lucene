package com.alogic.xscript.lucene.util.filter;


import org.apache.lucene.search.BooleanClause;

import com.alogic.xscript.lucene.util.FilterBuilder;

public class And extends FilterBuilder.Multi {

	@Override
	protected BooleanClause.Occur getOccur() {
		return BooleanClause.Occur.MUST;
	}
	
}
