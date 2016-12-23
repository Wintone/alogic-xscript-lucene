# alogic-xscript-lucene
基于xscript2.0的lucene插件

### Overview

alogic-xscript-lucene是基于xscript2.0的lucene插件，提供了使用lucene所需的相关指令。

### Getting started

按照以下步骤，您可轻松在您的项目中使用alogic-xscript-lucene.

不过开始之前，我们希望您了解xscript的相关知识。

- [xscript2.0](https://github.com/yyduan/alogic/blob/master/alogic-doc/alogic-common/xscript2.md) - 您可以了解xscript的基本原理及基本编程思路
- [xscript2.0基础插件](https://github.com/yyduan/alogic/blob/master/alogic-doc/alogic-common/xscript2-plugins.md) - 如何使用xscript的基础插件
- [基于xscript的together](https://github.com/yyduan/alogic/blob/master/alogic-doc/alogic-common/xscript2-together.md) - 如何将你的script发布为alogic服务


### Example

下面的案例是构建lucene索引.

```xml
<?xml version="1.0"?>
<script>
    <using xmlTag="indexConf" module="com.alogic.xscript.lucene.IndexConf"/>

    <indexConf indexDir="D:\\luceneIndex" analyzer="StandardAnalyzer">
    	<!-- create为false即批量增加索引,默认为false;
    		true即新建索引，如果是第一次用lucene建立索引，则必须设置为true -->
    	<idx-writer create="true">
	        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
	           type有text和Blob两种类型
	        -->        
	        <idx-new type="text" id="text1" value="hello,man!"/>
	        <idx-new type="text" id="text2" value="goodby,man!"/>
	        <idx-new type="text" id="text3" value="hello,woman!"/>
	        <idx-new type="text" id="text4" value="goodbye,woman!"/>   
        </idx-writer>   
    	 <idx-reader>
        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
        -->
        	<idx-query type="PrefixQuery" field="content" q="hel" />
        </idx-reader>
        <idx-writer>
        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
        -->
     	   <idx-update id="text4" value="hello,world,woman!" />
        </idx-writer>
        <idx-writer>
        <!-- 在这里你可以使用alogic-xcript-lucene提供的语句
        -->
       		<idx-delete field="content" q="man" />
        </idx-writer>
    </indexConf>
</script>
```

### 指令参考

参见[alogic-xscript-lucene参考](src/docs/reference.md)。