package com.alogic.xscript.lucene.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import com.alogic.lucene.core.IndexBuilder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class FileIndexBuilder extends IndexBuilder.Abstract{
	
	protected String dataDir = null;
	
	public FileIndexBuilder(String dir) {
		dataDir = dir;
	}

	protected String getDataDir() {
		return dataDir;
	}
	
	protected void onBuild(IndexWriter writer) {
		try {
			String content = "";
			File[] files = new File(dataDir).listFiles();
			for(File file : files) {
				content = "";
				//获取文件后缀
				String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				if("txt".equalsIgnoreCase(type)) {
					content += txt2String(file);
				} else if("doc".equalsIgnoreCase(type)) {
					content += doc2String(file);
				} else if("xls".equalsIgnoreCase(type)) {
					content += xls2String(file);
				}
				Document doc = newDocument();
				doc.add(new TextField("content", content, Field.Store.YES));
				doc.add(new TextField("fileName", file.getName(), Field.Store.YES));
				doc.add(new TextField("fullPath", file.getCanonicalPath(), Field.Store.YES));
				commitDocument(writer, doc);
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/*
	 * 读取xls文件内容
	 * @param file 想要读取的文件对象
	 * @return 返回文件内容
	 */
	public String xls2String(File file) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = Workbook.getWorkbook(fis);
			Sheet[] sheet = rwb.getSheets();
			for(int i = 0; i < sheet.length; i++) {
				Sheet rs = rwb.getSheet(i);
				for(int j = 0; j < rs.getRows(); j++) {
					Cell[] cells = rs.getRow(j);
					for(int k = 0; k < cells.length; k++) {
						sb.append(cells[k].getContents());
					}
				}
			}
			fis.close();
			result += sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 读取doc文件内容
	 * @param file 想要读取的文件对象
	 * @return 返回文件内容
	 */
	public String doc2String(File file) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			Range rang = doc.getRange();
			result += rang.text();
			fis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 读取txt文件的内容
	 * @param file 想要读取的文件对象
	 * @return 返回文件内容
	 */
	public  String txt2String(File file) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));   //构造一个BufferedReader类来读取文件
			String s = null;
			while((s = br.readLine()) != null) { //使用readLine方法， 一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
