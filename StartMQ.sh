#!/usr/bin/env bash

cd E:/RocketMQ/rocketmq-all-4.2.0-bin-release/bin
start mqnamesrv.cmd
start mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true