# Spark

## 安装

1. 下载

   https://spark.apache.org/downloads.html
   
2. 安装

   直接解压，有配置jdk环境变量的话，便可直接启动 spark 命令（没配置path的情况下，绝对路径方式启动）

## 程序打包

使用 maven shade 插件将程序打包成 uber-jar

> If your code depends on other projects, you will need to package them alongside your application in order to distribute the code to a Spark cluster. To do this, create an assembly jar (or “uber” jar) containing your code and its dependencies.

## 程序运行

例子

````
./FinnDev/spark-2.4.4-bin-hadoop2.7/bin/spark-submit \
--class finn.Application \
--master local \
--deploy-mode client \
/Users/development/IdeaProjects/sparksamples/target/sparksamples-1.0-SNAPSHOT.jar \
/Users/development/IdeaProjects/sparksamples/src/main/resources/input.txt 1
````

## 概念

RDD(resilient distributed dataset) 作为一个数据集类，类上的操作分为2种：

- transform: 将当前的rdd转换为新的rdd
- action：执行一项动作来获取一个结果

RDD上的操作 可以参考 jdk8 collection stream功能

> Sometimes, a variable needs to be shared across tasks, or between tasks and the driver program.
>
> Spark supports two types of shared variables: broadcast variables, which can be used to cache a value in memory on all nodes, and accumulators, which are variables that are only “added” to, such as counters and sums.

Accumulator 就是为了支持变量能够横跨 *集群各个节点* 来共享数据的


### 猜测:

1.RDD上的方法是可以发布到 *集群各个节点* 上并行执行的

2.`collect`方法是将发布到 *集群各个节点* 的`task result`收集回到`driver`(个人翻译为驱动程序，调度程序?)上处理
