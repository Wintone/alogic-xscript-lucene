idx-delete
========

idx-delete用于执行删除索引，删除某种域名中含有某个关键字的文档。
必须在idx-writer语句内使用。


### 实现类

com.alogic.xscript.lucene.IndexDelete


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | idx-writer的上下文对象id,缺省为$idx-writer | 
| 2 | field | 域名（可以为content、filename等）,必要 | 
| 3 | q | 关键字,必要 | 


### 案例

下面是一个创建idx-delete的案例：

```xml
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<idx-writer>
	        <idx-delete field="content" q="man" />
        </idx-writer>   
    </lucene>
</script>
```
