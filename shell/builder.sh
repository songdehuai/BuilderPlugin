#!/bin/bash
dir=$(
  # shellcheck disable=SC2046
  cd $(dirname "$PWD") || exit
  pwd -P
)
cd "$dir"
echo "$dir"
builderPath=.builder/
builderTask=.task
task="$1"
#清空内容
echo >"$dir"/$builderPath$builderTask
#创建任务列表
echo $task >"$dir"/$builderPath$builderTask
#通过gradle/gradlew调用自定义任务，进行打包
./gradlew ExportChannelTask