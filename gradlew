#!/bin/sh 
 
# Gradle start up script for UN*X 
 
APP_NAME="Gradle" 
APP_BASE_NAME=`basename "$0"` 
 
# Use the maximum available, or set MAX_FD != -1 to use that value. 
MAX_FD="maximum" 
 
warn ( ) { 
    echo "$*" 
} 
 
die ( ) { 
    echo 
    echo "$*" 
    echo 
    exit 1 
} 
 
# Determine the Java command to use to start the JVM. 
if [ -n "$JAVA_HOME" ] ; then 
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then 
        # IBM's JDK on AIX uses strange locations for the executables 
        JAVACMD="$JAVA_HOME/jre/sh/java" 
    else 
        JAVACMD="$JAVA_HOME/bin/java" 
    fi 
    if [ ! -x "$JAVACMD" ] ; then 
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" 
    fi 
else 
    JAVACMD="java" 
    which java  || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." 
fi 
 
# Escape application args 
save ( ) { 
    for i do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;$s/$/' \\\\/" ; done 
    echo " " 
} 
APP_ARGS=`save "$@"` 
 
# Collect all arguments for the java command 
set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS" 
 
exec "$JAVACMD" "$@" 
