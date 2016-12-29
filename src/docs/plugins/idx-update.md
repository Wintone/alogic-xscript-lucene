idx-update
========

idx-update用于执行更新索引，更新某个文档或文本变量中的值。
必须在idx-writer语句内使用。


### 实现类

com.alogic.xscript.lucene.IndexUpdate


### 配置参数

| 编号 | 代码 | 说明 |
| ---- | ---- | ---- |
| 1 | pid | idx-writer的上下文对象id,缺省为$idx-writer | 
| 2 | id | 文本类型的变量名,必要 | 
| 3 | value | 文本类型的值,必要 | 


### 案例

下面是一个创建idx-update的案例：

```xml
<script>
    <using xmlTag="lucene" module="com.alogic.xscript.lucene.IndexConf"/>

    <lucene indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<idx-writer>
	        <idx-update id="text4" value="hello,world,woman!" />
        </idx-writer>   
    </lucene>
</script>
```
