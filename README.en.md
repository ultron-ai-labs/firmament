English | [简体中文](./README.md)

# FIRMAMENT: AI Quantitative Trading System

FIRMAMENT：AI Quantitative Trading System

## Project Introduction

AI Quantitative Trading System Based on JDK8, SpringBoot, MongoDB, and AI Model
Node 20.0.1
The MVP version has been completed and the project has been split into two parts: the backend system (current project) and the frontend system (firm ui)

##Quick Start

1. Start MongoDB
2. Start the backend system, note that you need to enable the proxy, which can be modified to [FirmamentApplication.java](admin%2Fadmin-web%2Fsrc%2Fmain%2Fjava%2Fcn%2Forg%2Fultronai%2Ffirmament%2Fadmin%2Fweb%2FFirmamentApplication.java)
   The configuration
3. Start the front-end system
4. Register an account (account name must be admin, represented as administrator). The administrator identity can be configured in [application.yml](admin%2Fadmin-web%2Fsrc%2Fmain%2Fresources%2Fapplication.yml), separated by English
5. After registering an account, log in and enter the system homepage to configure AI models, notification models, prompt words, real-time AI transactions, and view orders


## Indicator for prompt words

At present, the indicators for prompt words are only written dead: EMA, MACD, RSI. If you want more indicators, you can calculate and develop them yourself (the author's time is limited)

## Overview of Built in Indicators in the Project

This project provides comprehensive financial market technical indicator calculation functions, including trend, momentum, volatility, trading volume, and other price derivative indicators. All indicators are based on OHLCV (opening price, highest price, lowest price, closing price, trading volume)
Data calculation can be used for quantitative trading strategy development, market analysis, and algorithmic trading systems.


## List of supported indicators

Built in indicators are based on Ta4j. For a detailed list of indicators, please refer to the official documentation! https://github.com/ta4j/ta4j-wiki

## Design philosophy

By obtaining real-time data from the exchange, calculating results through local indicators, and providing order and account data to AI models, order management is carried out based on AI model decisions, relying entirely on AI for risk control and order management.

## The backend operation diagram is as follows

![index.png](static%2Fimages%2Findex.png)
![personalCenter.png](static%2Fimages%2FpersonalCenter.png)
![alarm.png](static%2Fimages%2Falarm.png)
![aimodel.png](static%2Fimages%2Faimodel.png)
![prompt.png](static%2Fimages%2Fprompt.png)
![aiTrading_1.png](static%2Fimages%2FaiTrading_1.png)
![aiTrading_2.png](static%2Fimages%2FaiTrading_2.png)
![aiTrading_3.png](static%2Fimages%2FaiTrading_3.png)
![aiTrading_4.png](static%2Fimages%2FaiTrading_4.png)

## Future functions

Support more AI model integration
Support access to more exchanges
Support API access to exchanges
Support local strategies (develop more metrics based on Ta4j)
Support AI backtesting and local strategy backtesting