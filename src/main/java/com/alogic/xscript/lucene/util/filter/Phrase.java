package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * PhraseQuery(短语查询)
 *关键词值之间用‘|’分隔
 */

public class Phrase extends FilterBuilder.Abstract {

	protected Query filter;
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}
	
	@Override
	public String getOccur() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String q = PropertiesConstants.getString(p, "q", "");
		int slop = PropertiesConstants.getInt(p, "slot", 0);
		String[] keys = q.split("\\|");
		PhraseQuery.Builder phraseQuery = new PhraseQuery.Builder();
		phraseQuery.setSlop(slop);
		phraseQuery.add(new Term(field, keys[0]));
		phraseQuery.add(new Term(field, keys[1]));
		filter = phraseQuery.build();
	}

}
