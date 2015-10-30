#!/bin/sh
# ----------------------------------------------------------------------------
#  Copyright 2001-2006 The Apache Software Foundation.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
# ----------------------------------------------------------------------------
#
#   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
#   reserved.


# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`
BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`



# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  Darwin*) darwin=true
           if [ -z "$JAVA_VERSION" ] ; then
             JAVA_VERSION="CurrentJDK"
           else
             echo "Using Java version: $JAVA_VERSION"
           fi
           if [ -z "$JAVA_HOME" ] ; then
             JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
           fi
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# If a specific java binary isn't specified search for the standard 'java' binary
if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD=`which java`
  fi
fi

JAVACMD="$BASEDIR"/jre/linux/bin/java

#if [ ! -x "$JAVACMD" ] ; then
#  echo "Error: JAVA_HOME is not defined correctly." 1>&2
#  echo "  We cannot execute $JAVACMD" 1>&2
#  exit 1
#fi

if [ -z "$REPO" ]
then
  REPO="$BASEDIR"/lib
fi

CLASSPATH=$CLASSPATH_PREFIX:"$REPO"/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar:"$REPO"/org/eclipse/jetty/jetty-webapp/7.5.1.v20110908/jetty-webapp-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-xml/7.5.1.v20110908/jetty-xml-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-util/7.5.1.v20110908/jetty-util-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-servlet/7.5.1.v20110908/jetty-servlet-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-security/7.5.1.v20110908/jetty-security-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-server/7.5.1.v20110908/jetty-server-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-continuation/7.5.1.v20110908/jetty-continuation-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-http/7.5.1.v20110908/jetty-http-7.5.1.v20110908.jar:"$REPO"/org/eclipse/jetty/jetty-io/7.5.1.v20110908/jetty-io-7.5.1.v20110908.jar:"$REPO"/org/mortbay/jetty/jsp-2.1-glassfish/2.1.v20100127/jsp-2.1-glassfish-2.1.v20100127.jar:"$REPO"/org/eclipse/jdt/core/compiler/ecj/3.5.1/ecj-3.5.1.jar:"$REPO"/org/mortbay/jetty/jsp-api-2.1-glassfish/2.1.v20100127/jsp-api-2.1-glassfish-2.1.v20100127.jar:"$REPO"/ant/ant/1.6.5/ant-1.6.5.jar:"$REPO"/com/sun/jersey/jersey-servlet/1.18/jersey-servlet-1.18.jar:"$REPO"/com/sun/jersey/jersey-server/1.18/jersey-server-1.18.jar:"$REPO"/com/sun/jersey/jersey-core/1.18/jersey-core-1.18.jar:"$REPO"/com/sun/jersey/jersey-json/1.18/jersey-json-1.18.jar:"$REPO"/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar:"$REPO"/com/sun/xml/bind/jaxb-impl/2.2.3-1/jaxb-impl-2.2.3-1.jar:"$REPO"/javax/xml/bind/jaxb-api/2.2.2/jaxb-api-2.2.2.jar:"$REPO"/javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar:"$REPO"/javax/activation/activation/1.1/activation-1.1.jar:"$REPO"/org/codehaus/jackson/jackson-core-asl/1.9.2/jackson-core-asl-1.9.2.jar:"$REPO"/org/codehaus/jackson/jackson-mapper-asl/1.9.2/jackson-mapper-asl-1.9.2.jar:"$REPO"/org/codehaus/jackson/jackson-jaxrs/1.9.2/jackson-jaxrs-1.9.2.jar:"$REPO"/org/codehaus/jackson/jackson-xc/1.9.2/jackson-xc-1.9.2.jar:"$REPO"/com/sun/jersey/jersey-client/1.18/jersey-client-1.18.jar:"$REPO"/com/fasterxml/jackson/core/jackson-core/2.4.2/jackson-core-2.4.2.jar:"$REPO"/com/fasterxml/jackson/core/jackson-databind/2.4.2/jackson-databind-2.4.2.jar:"$REPO"/com/fasterxml/jackson/core/jackson-annotations/2.4.2/jackson-annotations-2.4.2.jar:"$REPO"/net/sf/json-lib/json-lib/2.4/json-lib-2.4-jdk15.jar:"$REPO"/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:"$REPO"/commons-lang/commons-lang/2.5/commons-lang-2.5.jar:"$REPO"/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar:"$REPO"/net/sf/ezmorph/ezmorph/1.0.6/ezmorph-1.0.6.jar:"$REPO"/org/mybatis/mybatis/3.2.4/mybatis-3.2.4.jar:"$REPO"/cglib/cglib/2.2.2/cglib-2.2.2.jar:"$REPO"/asm/asm/3.3.1/asm-3.3.1.jar:"$REPO"/mysql/mysql-connector-java/5.1.29/mysql-connector-java-5.1.29.jar:"$REPO"/com/sun/xml/bind/jaxb-xjc/2.2.1.1/jaxb-xjc-2.2.1.1.jar:"$REPO"/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:"$REPO"/xml-apis/xml-apis/1.0.b2/xml-apis-1.0.b2.jar:"$REPO"/commons-beanutils/commons-beanutils/1.8.3/commons-beanutils-1.8.3.jar:"$REPO"/commons-cli/commons-cli/1.2/commons-cli-1.2.jar:"$REPO"/commons-codec/commons-codec/1.9/commons-codec-1.9.jar:"$REPO"/commons-dbutils/commons-dbutils/1.5/commons-dbutils-1.5.jar:"$REPO"/commons-fileupload/commons-fileupload/1.3/commons-fileupload-1.3.jar:"$REPO"/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar:"$REPO"/commons-io/commons-io/2.4/commons-io-2.4.jar:"$REPO"/commons-jxpath/commons-jxpath/1.3/commons-jxpath-1.3.jar:"$REPO"/commons-net/commons-net/3.3/commons-net-3.3.jar:"$REPO"/commons-validator/commons-validator/1.4.0/commons-validator-1.4.0.jar:"$REPO"/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:"$REPO"/commons-vfs/commons-vfs/1.0/commons-vfs-1.0.jar:"$REPO"/commons-configuration/commons-configuration/1.7/commons-configuration-1.7.jar:"$REPO"/com/jcraft/jsch/0.1.51/jsch-0.1.51.jar:"$REPO"/log4j/log4j/1.2.12/log4j-1.2.12.jar:"$REPO"/org/slf4j/slf4j-api/1.6.6/slf4j-api-1.6.6.jar:"$REPO"/org/slf4j/slf4j-log4j12/1.6.6/slf4j-log4j12-1.6.6.jar:"$REPO"/junit/junit/4.11/junit-4.11.jar:"$REPO"/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:"$REPO"/com/culabs/unfvo/1.0.0/unfvo-1.0.0.jar
EXTRA_JVM_ARGUMENTS="-Xms128m"

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$HOME" ] && HOME=`cygpath --path --windows "$HOME"`
  [ -n "$BASEDIR" ] && BASEDIR=`cygpath --path --windows "$BASEDIR"`
  [ -n "$REPO" ] && REPO=`cygpath --path --windows "$REPO"`
fi

exec "$JAVACMD" $JAVA_OPTS \
  $EXTRA_JVM_ARGUMENTS \
  -classpath "$CLASSPATH" \
  -Dapp.name="startup" \
  -Dapp.pid="$$" \
  -Dapp.repo="$REPO" \
  -Dapp.home="$BASEDIR" \
  -Dbasedir="$BASEDIR" \
  com.culabs.nfvo.NFVOBootstrap \
  "$@"
