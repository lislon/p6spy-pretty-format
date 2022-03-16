# p6spy-pretty-format
Quick & dirty pretty single line printer for [p6spy](https://p6spy.readthedocs.io/), that allows to print SQL logs with parameters.

Example of hibernate logs:

```
23:27:47.595 0ms 
   select
       this_.id as id1_197_0_,
       this_.name as name2_197_0_,
       this_.forDisplay as fordispl3_197_0_ 
   from
       categorygroup this_ 
   where
       this_.name='Holds'
   ```

*Note:* This is tested only for Java 8 + Apache tomcat

## Usage

### For apache projects

1. Copy [p6spy-pretty-formatter-0.1.0-jar-with-dependencies.jar](https://github.com/lislon/p6spy-pretty-format/releases/download/0.1.0/p6spy-pretty-formatter-0.1.0-jar-with-dependencies.jar) to `$CATALINA_HOME` or Global Libraries in IntelliJ IDEA

2. Save file `p6spy.properties` to fylesystem with following contents:
    ```
    driverlist=com.mysql.jdbc.Driver
    appender=com.p6spy.engine.spy.appender.StdoutLogger
    logMessageFormat=org.lislon.p6spy.PrettyFormat
    ```
3. Add to JVM args
```
-Dspy.properties=c:/Users/myuser/spy.properties
-Dspy.lislon.pretty=SINGLE
```
4. Replace URL of JDBC connection in application configuration
```
jdbc:mysql://${mysql.hostname}:${mysql.port}/dbname
→
jdbc:p6spy:mysql://${mysql.hostname}:${mysql.port}/dbname
```

More detailed instruction: https://p6spy.readthedocs.io/


### Supported logging formats

You can change between single/multiline formats by modifying JVM parameter:

- `-Dspy.lislon.pretty=MULTI`
   ```
   23:27:47.595 0ms 
       select
           this_.id as id1_197_0_,
           this_.name as name2_197_0_,
           this_.forDisplay as fordispl3_197_0_ 
       from
           categorygroup this_ 
       where
           this_.name='Holds'
   ```
- `-Dspy.lislon.pretty=SINGLE` single line logs
```
23:30:58.758 0ms select this_.id as id1_197_0_, this_.name as name2_197_0_, this_.forDisplay as fordispl3_197_0_ from categorygroup this_ where this_.name='Holds'
```
