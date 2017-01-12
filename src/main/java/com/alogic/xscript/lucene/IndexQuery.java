package com.alogic.xscript.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.w3c.dom.Element;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.lucene.util.FilterBuilder;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.XmlElementProperties;
import com.anysoft.util.XmlTools;

public class IndexQuery extends IndexReaderOperation {
	
	/**
	 * Filter Builder
	 */
	protected FilterBuilder fb = null;
	
	/**
	 * 查询类型，分别有QueryParser、MultiFieldQueryParser、TermQuery、
	 * PrefixQuery、PhraseQuery、WildcardQuery、TermRangeQuery、NumericRangeQuery、
	 * BooleanQuery共9种查询类型
	 */
	protected String type = null;
	
	/**
	 * 索引查询域
	 */
	protected String field = null;
	
	/**
	 * 查询关键字
	 */
	protected String q = null;

	public IndexQuery(String tag, Logiclet p) {
		super(tag, p);
	}
	
	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		type = p.GetValue("type", type, false, true);
		field = p.GetValue("field", field, false, true);
		q = p.GetValue("q", q, false, true);
	}	

	@Override
	public void configure(Element e, Properties p) {
		Properties props = new XmlElementProperties(e, p);

		Element filter = XmlTools.getFirstElementByPath(e, "filter");
		if (filter != null) {
			FilterBuilder.TheFactory f = new FilterBuilder.TheFactory();
			try {
				fb = f.newInstance(filter, props, "module");
			} catch (Exception ex) {
				log("Can not create instance of FilterBuilder.", "error");
			}
		}
		configure(props);
	}
	
	@Override
	protected void onExecute(IndexReader indexReader, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			String tagValue = ctx.transform(tag);	

			if(fb == null) {
				//简单查询的时候，field,q,type三个参数缺一不可
				if(StringUtils.isEmpty(field) || StringUtils.isEmpty(q) || StringUtils.isEmpty(type))
					throw new BaseException("core.not_enought_parameters", "Not enought parameters,check your script.");
			} else {
				//含有过滤器的查询的时候，简单查询可有可无（field,q,type三个参数或都存在或都不存在）
				if(!((StringUtils.isEmpty(field) && StringUtils.isEmpty(q) && StringUtils.isEmpty(type)) || 
						(!StringUtils.isEmpty(field) && !StringUtils.isEmpty(q) && !StringUtils.isEmpty(type)))) {
					throw new BaseException("core.not_enought_parameters", "Not enought parameters,check your script.");
				}
			}
			String fieldType = null;
			if(field != null) {
				fieldType = ctx.transform(field);
			}
			String queryStr = null;
			if(q != null) {
				queryStr = ctx.transform(q);
			}
			Analyzer analyzer = ctx.getObject("analyzer");
			Query query = null;
			if( type != null ) {
				switch(ctx.transform(type)) {
					case "QueryParser":
						query = new QueryParser(fieldType, analyzer).parse(queryStr);
						break;
					case "MultiFieldQueryParser":
						String[] fields = fieldType.split("\\|");
						query = new MultiFieldQueryParser(fields, analyzer).parse(queryStr);
						break;
					case "TermQuery":
						query = new TermQuery(new Term(fieldType, queryStr));
						break;
					case "PrefixQuery":
						query = new PrefixQuery(new Term(fieldType, queryStr));
						break;
					case "PhraseQuery":
						break;
					case "WildcardQuery":
						query = new WildcardQuery(new Term(fieldType, queryStr));
						break;
					case "TermRangeQuery":
						break;
					case "NumericRangeQuery":
						break;
					case "BooleanQuery":
						break;
					default:
						throw new BaseException("core.not_correct_queryType", "Not correct queryType,check your script.");
				}
			}
			Query filter = null;
			if (fb != null) {
				filter = fb.getFilter(ctx);
			}
			BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
			if(query != null) {
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			if(filter != null) {
				booleanQuery.add(filter, BooleanClause.Occur.MUST);
			}
	
			IndexSearcher searcher = new IndexSearcher(indexReader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(1024);
			searcher.search(booleanQuery.build(), collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			   
			List<Object> list = new ArrayList<Object>();
			for (ScoreDoc doc:hits){
				 int docId = doc.doc;
			     Document d = searcher.doc(docId);
			     list.add(d.get("fileName"));
			}
			current.put(tagValue, list);
		}catch (Exception exc){
			logger.error("Search error "+exc);
			exc.printStackTrace();
		}
	}

}
