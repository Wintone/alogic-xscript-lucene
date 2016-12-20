package com.alogic.xscript.lucene;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;

import com.alogic.lucene.core.Indexer;
import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.plugins.Segment;
import com.anysoft.util.BaseException;
import com.anysoft.util.IOTools;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IdxReader extends Segment {
	
	protected String pid = "$lucene";
	protected String cid = "$idx-reader";

	public IdxReader(String tag, Logiclet p) {
		super(tag, p);
		
		registerModule("idx-query", IndexQuery.class);
	}
	
	public void configure(Properties p) {
		super.configure(p);
		
		pid = PropertiesConstants.getString(p, "pid", pid, true);
		cid = PropertiesConstants.getString(p, "cid", cid, true);
	}
	
	@Override
	protected void onExecute(Map<String, Object> root, 
			Map<String, Object> current, LogicletContext ctx, ExecuteWatcher watcher) {
		Indexer.Abstract indexer = ctx.getObject(pid);
		if(indexer == null) {
			throw new BaseException("core.no_luceneIndexer", "It must in a lucene context, check your script.");
		}
		
		IndexReader indexReader = indexer.newReader();	
		Analyzer analyzer = indexer.getAnalyzer();
		
		try {
			ctx.setObject(cid, indexReader);
			ctx.setObject("analyzer", analyzer);
			super.onExecute(root, current, ctx, watcher);
		} finally {
			ctx.removeObject(cid);
			ctx.removeObject("analyzer");
			IOTools.close(indexReader);
		}
	}

}
