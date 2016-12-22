package com.alogic.xscript.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.anysoft.util.Properties;
import com.anysoft.util.XmlElementProperties;
import com.anysoft.util.XmlTools;

public class IndexQuery extends IndexReaderOperation {
	
	/**
	 * Filter Builder
	 */
	protected FilterBuilder fb = null;
	
	protected String type = null;
	protected String field = "content";
	protected String q = null;
	
	protected static HashMap<String, Integer> map = new HashMap<String,Integer>();
	
	static {
		map.put("QueryParser", 0);
		map.put("MultiFieldQueryParser", 1);
		map.put("TermQuery", 2);
		map.put("PrefixQuery", 3);
		map.put("PhraseQuery", 4);
		map.put("WildcardQuery", 5);
		map.put("TermRangeQuery", 6);
		map.put("NumericRangeQuery", 7);
		map.put("BooleanQuery", 8);
	}

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
			String fieldType = null;
			if(field != null) {
				fieldType = ctx.transform(field);
			}
			String queryStr = null;
			if(q != null) {
				queryStr = ctx.transform(q);
			}
			int queryType = -1;
			if( type != null ) {
				queryType = map.get(ctx.transform(type));
			}
			
			Analyzer analyzer = ctx.getObject("analyzer");
			Query query = null;
			switch(queryType) {
				case  0:
					query = new QueryParser(fieldType, analyzer).parse(queryStr);
					break;
				case 1:
					String[] fields = fieldType.split("\\|");
					query = new MultiFieldQueryParser(fields, analyzer).parse(queryStr);
					break;
				case 2:
					query = new TermQuery(new Term(fieldType, queryStr));
					break;
				case 3:
					query = new PrefixQuery(new Term(fieldType, queryStr));
					break;
				case 4:
					break;
				case 5:
					query = new WildcardQuery(new Term(fieldType, queryStr));
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					break;
				default:
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
			     list.add(d.get("filename"));
			}
			current.put(tagValue, list);
		}catch (Exception exc){
			logger.error("Search error "+exc);
		}
	}

}
