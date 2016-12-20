package com.alogic.xscript.lucene.util;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

import com.logicbus.dbcp.context.DbcpSource;
import com.logicbus.dbcp.core.ConnectionPool;

public class BlobIndexBuilder{

	protected static String dbcpId = null;
	protected static String sqlQueryBlobData = null;
	
	public BlobIndexBuilder(String id, String value) {
		dbcpId = id;
		sqlQueryBlobData = value;
	}

	protected String getDbcpId() {
		return dbcpId;
	}
	
	protected String getSqlQueryBlobData() {
		return sqlQueryBlobData;
	}
	
	public void addDocument(IndexWriter writer) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			
			 ConnectionPool pool = getConnectionPool();
			 Connection conn = pool.getConnection();

			ps = conn.prepareStatement(sqlQueryBlobData);
			rs = ps.executeQuery();
			while (rs.next()) {

				String fileId = rs.getString("blob_id");
				Blob blob = rs.getBlob("data");
				String content = new String(blob.getBytes((long) 1, (int) blob.length()));
				String fileName = rs.getString("file_name");
				String versionId = rs.getString("version_id");
				if (StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(versionId)) {
					Document doc = new Document();
					doc.add(new TextField("fileId", fileId, Field.Store.YES));
					doc.add(new TextField("content", content, Field.Store.YES));
					doc.add(new TextField("fileName", fileName, Field.Store.YES));
					doc.add(new TextField("versionId", versionId, Field.Store.YES));
					writer.addDocument(doc);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	
	protected static ConnectionPool getConnectionPool() {
		return DbcpSource.getPool(dbcpId);
	}
	
}
