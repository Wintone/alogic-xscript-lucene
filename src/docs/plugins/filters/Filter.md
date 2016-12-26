Filter
======

Filter主要用于对lucene的查询结果进行过滤，主要应用于idx-query查询操作中。

当前支持的Filter包括：

- [Must](Must.md) --应用BooleanQuery进行组合查询时,条件之间的关系是由类BooleanClause.Occur值为Must
- [MustNot](MustNot.md) --应用BooleanQuery进行组合查询时,条件之间的关系是由类BooleanClause.Occur值为Must_Not
- [Should](Should.md) --应用BooleanQuery进行组合查询时,条件之间的关系是由类BooleanClause.Occur值为Should

