# Arthas精讲

## 前言

### 简介

[Arthas](https://arthas.aliyun.com/doc/) 是Alibaba开源的Java诊断工具，深受开发者喜爱。

- 这个类从哪个 jar 包加载的？为什么会报各种类相关的 Exception？
- 我改的代码为什么没有执行到？难道是我没 commit？分支搞错了？
- 遇到问题无法在线上 debug，难道只能通过加日志再重新发布吗？
- 线上遇到某个用户的数据处理有问题，但线上同样无法 debug，线下无法重现！
- 是否有一个全局视角来查看系统的运行状况？
- 有什么办法可以监控到JVM的实时运行状态？
- 怎么快速定位应用的热点，生成火焰图？
- 怎样直接从JVM内查找某个类的实例？

### 本文说明

本教程适用于有实际开发经验的开发者。本文主要结合工作中的使用场景介绍常用命令，快速上手。学习完成后你可以熟练使用arthas排查Java程序线上问题。本文中介绍的所有命令都不需要记忆，你可以在 [github](https://github.com/wearexteam/teach-demo)
或者官网查询相关命令。

## 快速上手

### 下载安装

arthas是一个jar包，将其下载到本地目录即可。
mac m1芯片下载地址[arthas 3.5.5](https://github.com/alibaba/arthas/releases)

```shell
curl -O https://arthas.aliyun.com/arthas-boot.jar
```

下载并打包实验所需要的Java代码，配置好maven和java，java版本采用11，可在pom.xml修改版本。

```shell
#下载
git clone git@github.com:wearexteam/teach-demo.git
#打包
cd teach-demo 
mvn clean package
```

运行arthas和实验程序

```shell
#启动实验程序
java -jar target/teach-demo-0.0.1-SNAPSHOT.jar
#启动arthas
java -jar artahs-boot.jar
```

## 基本命令

```shell
#实时数据看板
dashboard

#查看线程信息
thread

#查看jvm参数
jvm

#环境信息
sysenv

#帮助
help

#反编译类信息
jad com.teach.arthas.PeopleService

#查找加载的类信息
sc -d com.teach.arthas.PeopleService

#退出
stop
```

## 进阶命令

### watch

```shell
#基本使用
watch com.teach.arthas.PeopleService filter '{params,returnObj,throwExp}'  -n 5  -x 3 
# 参数 -b -e -s -f

#查看返回
watch com.teach.arthas.PeopleService filter '{params[2],returnObj,throwExp}'  -n 1  -x 3 -b

watch com.teach.arthas.PeopleService filter '{params[2][0]}'  -n 1  -x 3 -b

#根据参数过滤
watch com.teach.arthas.PeopleService filter '{params}'  -n 1  -x 3 -b 'params[0] < 20'

watch com.teach.arthas.PeopleService filter '{params}'  -n 1  -x 3 -b 'params[1].equals("li")'

#对结果进行过滤
watch com.teach.arthas.PeopleService filter '{params[2][0].{? #this.age < 30}}'  -n 2  -x 3 -b

```

### trace

```shell

trace com.teach.arthas.PeopleService filter  -n 5  

#根据耗时过滤
trace com.teach.arthas.PeopleService filter  -n 5 '#cost>200'

#根据参数条件拦截
trace com.teach.arthas.PeopleService filter  -n 5 'params[2][0].age > 30' -v

trace com.teach.arthas.PeopleService filter  -n 5 '(params[2][0].age > 30) && (#cost>600)' -v

```

### vmtool

```shell
vmtool -x 3 --action getInstances --className com.teach.arthas.PeopleService  

vmtool -x 3 --action getInstances --className com.teach.arthas.PeopleService  --express 'instances[0].filter(0," ",{})' 

vmtool -x 3 --action getInstances -c 724af044 --className com.teach.arthas.PeopleService  --express '#p=new com.teach.arthas.People(),#p.age=12,#p.name="wang",#list=new java.util.ArrayList(),#list.add(#p),instances[0].filter(0,"wang", #list)' 


```

### ognl

```shell

ognl -x 3 '@com.teach.arthas.PeopleService@makePeople()' -c 724af044

ognl -x 2 -c 55f3ddb1 '@com.teach.arthas.AppContextUtils@context.getBean(@com.teach.arthas.PeopleService@class).filter(0," ",{})'

```

> [Arthas官网](https://arthas.aliyun.com/en-us/)
>
>[Arthas问题排查集活用ognl表达式](https://github.com/alibaba/arthas/issues/11)