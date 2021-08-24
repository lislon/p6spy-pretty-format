# p6spy-pretty-format
Quicky & dirty pretty single line printer for [p6spy](https://p6spy.readthedocs.io/), that allows to print SQL logs with parameters.

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

1. Move [p6spy-pretty-formatter-0.1.0-jar-with-dependencies.jar](https://github.com/lislon/p6spy-pretty-format/releases/download/0.1.0/p6spy-pretty-formatter-0.1.0-jar-with-dependencies.jar) to `$CATALINA_HOME`. The fat jar includes:
   1. hibernate of random version (5.5.5.Final)
   2. p6spy (3.9.1)
   3. `org.lislon.p6spy.PrettyFormat` class
2. Add somewhere file `p6spy.properties` with following contents:
    ```
    driverlist=com.mysql.jdbc.Driver
    appender=com.p6spy.engine.spy.appender.StdoutLogger
    logMessageFormat=org.lislon.p6spy.PrettyFormat
    ```
3. Add JVM/Env variable `spy.properties` with path to `spy.properties`, i.e. `c:/Users/myuser/spy.properties`
4. Add p6spy to project's JDBC url like this `jdbc:p6spy:mysql://${mysql.hostname}:${mysql.port}/dbname`

More detailed instruction: https://p6spy.readthedocs.io/


### Chaning format

Set `spy.lislon.pretty` JVM/Env with value:

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
