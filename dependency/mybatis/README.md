


### 使用说明

#### 1、需要更换targetProject 里面的值，改为自己工程对应的目录

```
 <!--生成的dataobject 包路径 -->
        <javaModelGenerator targetPackage="com.quancheng.hemera.dao.dataobject" targetProject="xxxx/dao/src/main/java">
			<property name="enableSubPackages" value="true" />
			<property name="trimStrings" value="false" />
		</javaModelGenerator>

        <!--生成xml mapper文件 路径 -->
        <sqlMapGenerator targetPackage="src/main/resources/mybatis" targetProject="xxxx/dao">
			<property name="enableSubPackages" value="true" />
		</sqlMapGenerator>

        <!-- 生成的Dao接口 的包路径 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.quancheng.hemera.dao" targetProject="xxx/dao/src/main/java">
            <property name="enableSubPackages" value="ture"/>
        </javaClientGenerator>
```

#### 2、更换表名

```
table tableName="xxxx"
            enableUpdateByExample="false" enableDeleteByExample="false" 
            enableSelectByExample="false" selectByExampleQueryId="false" />

```

#### 3、运行 run.sh