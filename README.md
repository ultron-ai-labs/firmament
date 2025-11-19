简体中文 | [English](./README.en.md)

# 苍穹：AI量化交易系统

苍穹（firmament）：AI量化交易系统

## 项目简介

基于JDK8、SpringBoot、MongoDB、AI Model 的AI量化交易系统
Node 20.0.1
目前完成了MVP的版本，项目拆分为2个部分：分别是后端系统（当前项目）和前端系统（firmament-ui）

## 快速启动

1. 启动MongoDB
2. 启动后端系统，注意，你需要开启代理，可以修改 [FirmamentApplication.java](admin%2Fadmin-web%2Fsrc%2Fmain%2Fjava%2Fcn%2Forg%2Fultronai%2Ffirmament%2Fadmin%2Fweb%2FFirmamentApplication.java)
的配置
3. 启动前端系统
4. 注册账号（账户名需为admin，代表为管理员）管理员身份可以在[application.yml](admin%2Fadmin-web%2Fsrc%2Fmain%2Fresources%2Fapplication.yml)进行配置，以英文,分隔
5. 注册账号之后登录，进入系统首页，配置AI模型、配置通知模型、配置提示词、配置实时AI交易、即可查看订单

## 提示词指标

目前提示词指标只写死了：EMA、MACD、RSI 如果你想要更多的指标，可以自行计算开发即可（作者时间有限～）

## 项目内置指标概述

本项目提供了全面的金融市场技术指标计算功能，包括趋势类、动能类、波动率类、成交量类和其他价格衍生指标。所有指标都基于OHLCV(开盘价、最高价、最低价、收盘价、成交量)
数据进行计算，可以用于量化交易策略开发、市场分析和算法交易系统。

## 支持的指标列表

内置指标基于：基于Ta4j 详细的指标列表参见官方文档！ https://github.com/ta4j/ta4j-wiki

## 设计思想

通过实时获取交易所的数据，通过本地指标计算出来结果，然后将订单数据和账户数据提供给AI模型，根据AI模型的决策进行订单管理，完全依靠AI进行风控、订单管理。

## 未来功能

支持更多的AI模型接入
支持更多交易所接入
支持交易所API接入
支持本地策略（基于Ta4j开发更多指标）
支持AI回测和本地策略回测