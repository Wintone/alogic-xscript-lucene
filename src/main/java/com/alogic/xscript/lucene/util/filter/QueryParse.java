package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * QueryParser单域查询
 *
 */
public class QueryParse extends FilterBuilder.Abstract {
	
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
		String analyzerStr = PropertiesConstants.getString(p, "analyzer", "StandardAnalyzer");
		occur = PropertiesConstants.getString(p, "occur", "MUST");
		
		Analyzer analyzer = null;
		if(analyzerStr.equals("StandardAnalyzer")) {
			analyzer = new StandardAnalyzer();
		} else if(analyzerStr.equals("SmartChineseAnalyzer")) {
			analyzer = new SmartChineseAnalyzer();
		}
		try {
			filter = new QueryParser(field, analyzer).parse(q);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
