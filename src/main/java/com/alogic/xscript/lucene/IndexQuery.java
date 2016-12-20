package com.alogic.xscript.lucene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IndexQuery extends IndexReaderOperation {
	
	
	protected String field = "content";
	protected String queryStr = null;

	public IndexQuery(String tag, Logiclet p) {
		super(tag, p);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		field = PropertiesConstants.getString(p, "field", field, true);
		queryStr = PropertiesConstants.getString(p, "q", queryStr, true);
	}	

	@Override
	protected void onExecute(IndexReader indexReader, Map<String, Object> root, Map<String, Object> current,
			LogicletContext ctx, ExecuteWatcher watcher) {
		try {
			String tagValue = ctx.transform(tag);			
			Map<String, List<Object>> data = new HashMap<String, List<Object>>();
			IndexSearcher searcher = new IndexSearcher(indexReader);
			
			Analyzer analyzer = ctx.getObject("analyzer");
			Query query = new QueryParser(field,analyzer).parse(queryStr);
			
			TopScoreDocCollector collector = TopScoreDocCollector.create(1024);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			    
			List<Object> list = new ArrayList<Object>();
			for (ScoreDoc doc:hits){
				 int docId = doc.doc;
			     Document d = searcher.doc(docId);
			     list.add(d.get("fileName"));
			}
			data.put(queryStr, list);
			current.put(tagValue, data);
		}catch (Exception exc){
			logger.error("Search error"+exc);
		}
	}

}
