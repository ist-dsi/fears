#!/bin/sh
APPDIR=`dirname $0`;
java  -cp "$APPDIR/src:$APPDIR/bin:$APPDIR/lib/gwt-user.jar:$APPDIR/lib/gwt-linux/gwt-dev-linux.jar" com.google.gwt.dev.GWTShell -out "$APPDIR/www" "$@" eu.ist.fears.Fears/Fears.html;