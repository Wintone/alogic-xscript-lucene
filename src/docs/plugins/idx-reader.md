idx-reader
=======

idx-reader是一个类(IndexReader)表示对索引的读操作。

这个类属于org.apache.lucene.index包。

使用这个类，可以执行对索引的读操作，即根据关键字进行各种不同类型的查询操作。





### 实现类

com.alogic.xscript.lucene.IndexReader


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | lucene的上下文对象id,缺省为$lucene |
| 2 | cid | idx-reader的上下文对象id,缺省为$idx-reader | 


### 案例

下面是一个创建idx-reader的案例：

```xml
<script>
    <using xmlTag="indexConf" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">  
    	 <idx-reader>
	        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	        	第一种情况，没有过滤器的简单查询
	        	第二种情况，过滤器查询
	        -->
       		<!-- <idx-query type="TermQuery" field="content" q="man"/> -->
        	<idx-query>
        		<filter module="Multi">
        			<filter module="QueryParse" occur="MUST" field="content" q="woman"/>
        			<filter module="QueryParse" occur="MUST_NOT" field="content" q="man"/>
        		</filter>
        	</idx-query>
        </idx-reader>
    </lucene>
</script>
```
