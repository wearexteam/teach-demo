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

结合工作中遇到的使用场景介绍工作中常用命令，适用于快速上手。学习完成后你可以熟练使用arthas排查Java程序线上问题。本文中介绍的所有命令都不需要记忆，你可以在 [github](https://github.com/wearexteam/teach-demo)
或者官网查询相关命令。

## 快速上手

### 下载安装

arthas是一个jar包，将其下载到本地目录即可。

```shell
curl -O https://arthas.aliyun.com/arthas-boot.jar
```

下载并打包实验所需要的Java代码。

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
