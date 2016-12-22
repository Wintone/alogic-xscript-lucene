package com.alogic.xscript.lucene.util.filter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

/**
 * MultiFieldQueryParser
 * 多域查询,每个域之间用‘|’分隔
 *
 */
public class MultiField extends FilterBuilder.Abstract {

	protected Query filter;
	
	@Override
	public Query getFilter(Properties p) {
		return filter;
	}

	@Override
	public void configure(Properties p) {
		String field = PropertiesConstants.getString(p, "field", "content");
		String q = PropertiesConstants.getString(p, "q", "");
		String[] fields = field.split("\\|");
		String analyzerStr = PropertiesConstants.getString(p, "analyzer", "StandardAnalyzer");
		Analyzer analyzer = null;
		if(analyzerStr.equals("StandardAnalyzer")) {
			analyzer = new StandardAnalyzer();
		} else if(analyzerStr.equals("SmartChineseAnalyzer")) {
			analyzer = new SmartChineseAnalyzer();
		}
		try {
			filter = new MultiFieldQueryParser(fields, analyzer).parse(q);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
