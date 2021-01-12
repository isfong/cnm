#!/bin/bash
existProxy=$(cat /etc/nginx/nginx.conf|grep 'proxy_pass')
if [[ "$existProxy" = "" ]]; then
  /bin/cors.sh
fi