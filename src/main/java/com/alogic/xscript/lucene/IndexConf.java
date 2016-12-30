package com.alogic.xscript.lucene;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alogic.lucene.core.Indexer;
import com.alogic.lucene.indexer.FS;
import com.alogic.xscript.ExecuteWatcher;
import com.alogic.xscript.Logiclet;
import com.alogic.xscript.LogicletContext;
import com.alogic.xscript.plugins.Segment;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;
import com.anysoft.util.PropertiesConstants;

public class IndexConf extends Segment{
	
	protected String cid = "$lucene";
	protected String indexDir = null;
	protected String analyzer = null;

	public IndexConf(String tag, Logiclet p) {
		super(tag, p);
		
		registerModule("idx-writer", IdxWriter.class);
		registerModule("idx-reader", IdxReader.class);
	}

	@Override
	public void configure(Properties p) {
		super.configure(p);
		
		cid = PropertiesConstants.getString(p, "cid", cid, true);
		indexDir = p.GetValue("indexDir", indexDir, false, true);
		analyzer = p.GetValue("analyzer", analyzer, false, true);
	}
	
	@Override
	protected void onExecute(Map<String, Object> root, 
			Map<String, Object> current, LogicletContext ctx, ExecuteWatcher watcher) {
		Indexer.Abstract indexer = null;
		if (StringUtils.isEmpty(indexDir)) {
			throw new BaseException("core.no_indexDir", "check your script.");
		}
		if (StringUtils.isEmpty(analyzer)) {
			throw new BaseException("core.no_analyzer", "check your script.");
		}
		try {
			String dir = ctx.transform(indexDir);
			String analyzerType = ctx.transform(analyzer);
			indexer = new FS(dir, analyzerType);
		} catch(IOException e) {
			logger.error("Error" + e);
		}
		
		try {
			ctx.setObject(cid, indexer);
			super.onExecute(root, current, ctx, watcher);
		} finally {
			ctx.removeObject(cid);
		}
	}
}
