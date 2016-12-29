idx-writer
=======

idx-writer是一个类(indexWriter)表示对索引的写操作（包括新建、添加、更新和删除）。
这个类属于org.apache.lucene.index包。
使用这个类，可以执行对索引的写操作。
新建、添加、更新和删除索引等操作。




### 实现类

com.alogic.xscript.lucene.IdxWriter


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | lucene的上下文对象id,缺省为$lucene |
| 2 | cid | idx-writer的上下文对象id,缺省为$idx-writer | 
| 3 | create | 该参数只用于建立索引的时候，create为false即批量增加索引,缺省为false;true即新建索引，如果是第一次用lucene建立索引，则必须设置为true |


### 案例

下面是一个创建idx-writer的案例：

```xml
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<!-- create为false即批量增加索引,默认为false;
    		true即新建索引，如果是第一次用lucene建立索引，则必须设置为true -->
    	<idx-writer create="true">
	        <idx-new type="text" id="text1" value="hello,man!"/>
	        <idx-delete field="content" q="man" />
	        <idx-update id="text4" value="hello,world,woman!" />  
        </idx-writer>   
    </lucene>
</script>
```
