#!/bin/bash - 
export LANG=zh_CN.UTF-8


set -o nounset                              # Treat unset variables as an error
java -jar mybatis-generator-core-1.3.2.jar -configfile ./generatorConfig.xml -overwrite

