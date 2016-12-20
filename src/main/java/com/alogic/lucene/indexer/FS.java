package com.alogic.lucene.indexer;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.alogic.lucene.core.Indexer;
import com.anysoft.util.BaseException;
import com.anysoft.util.Properties;


/**
 * 基于FSDirectory的Indexer
 * 
 * @author duanyy
 *
 */
public class FS extends Indexer.Abstract{
	/**
	 * dircectory
	 */
	protected Directory index;
	protected String analyzer;
	
	public FS(String indexDir, String analyzerType) throws IOException {
		index = FSDirectory.open(new File(indexDir).toPath());
		analyzer = analyzerType;
	}
	
	public Directory getDirectory() {
		return index;
	}
	
	public void configure(Properties p) throws BaseException {
		super.configure(p);
		
		build(true);
	}
}
