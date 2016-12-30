idx-new
========

idx-new用于执行创建索引。

必须在idx-writer语句内使用。


### 实现类

com.alogic.xscript.lucene.IndexNew


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | idx-writer的上下文对象id,缺省为$idx-writer | 
| 2 | type | 需要创建索引的数据类型，有文本类型和Blob等,必要 | 
| 3 | id | 文本类型的变量名,必要 | 
| 4 | value | 文本类型的值,必要 | 


### 案例

下面是一个创建idx-new的案例：

```xml
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<!-- create为false即批量增加索引,默认为false;
    		true即新建索引，如果是第一次用lucene建立索引，则必须设置为true -->
    	<idx-writer create="true">
    		  <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	           type有text和Blob两种类型
	        -->  
	        <idx-new type="text" id="text1" value="hello,man!"/>
        </idx-writer>   
    </lucene>
</script>
```
