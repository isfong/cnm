#!/bin/bash
rm -rf /etc/nginx/nginx.conf
echo 'worker_processes      1;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo 'events {' >> /etc/nginx/nginx.conf
echo '  worker_connections  1024;' >> /etc/nginx/nginx.conf
echo '}' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo 'http {' >> /etc/nginx/nginx.conf
echo '  include             mime.types;' >> /etc/nginx/nginx.conf
echo '  default_type        application/octet-stream;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  sendfile on;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  keepalive_timeout   65;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  gzip on;' >> /etc/nginx/nginx.conf
echo '  gzip_static on;' >> /etc/nginx/nginx.conf
echo '  gzip_disable "msie6";' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  gzip_vary on;' >> /etc/nginx/nginx.conf
echo '  gzip_types text/plain text/css application/javascript;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  map $request_method $access_control_max_age {' >> /etc/nginx/nginx.conf
echo '    OPTIONS 1728000; # 20 days' >> /etc/nginx/nginx.conf
echo '  }' >> /etc/nginx/nginx.conf
echo '  server_tokens off; # Hide Nginx version' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '  server {' >> /etc/nginx/nginx.conf
echo '    listen            8080;' >> /etc/nginx/nginx.conf
echo '    server_name       localhost;' >> /etc/nginx/nginx.conf
echo '    index             index.html index.htm;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '    location / {' >> /etc/nginx/nginx.conf
echo '      absolute_redirect off;' >> /etc/nginx/nginx.conf
echo '      alias            /usr/share/nginx/html/;' >> /etc/nginx/nginx.conf
echo '      expires 1d;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '      location ~* \.(?:json|yml|yaml)$ {' >> /etc/nginx/nginx.conf
echo '        root /resources/;' >> /etc/nginx/nginx.conf
echo '        expires -1;' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '        include cors.conf;' >> /etc/nginx/nginx.conf
echo '      }' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
echo '      include cors.conf;' >> /etc/nginx/nginx.conf
echo '    }' >> /etc/nginx/nginx.conf
echo '' >> /etc/nginx/nginx.conf
corsUrls=${CORS//,/ }
for corsUrl in $corsUrls; do
    pathArray=(${corsUrl//\// })
    index=0
    location="    location "
    for  pathItem in ${pathArray[@]}; do
        if [[ $index != 0 && $index != 1 ]]; then
           location="$location/$pathItem"
        fi
        let index++
    done
    echo "$location {" >> /etc/nginx/nginx.conf
    echo "	  proxy_pass "${pathArray[0]}//${pathArray[1]}';' >> /etc/nginx/nginx.conf
    echo "    }" >> /etc/nginx/nginx.conf
    echo "" >> /etc/nginx/nginx.conf
done

echo '  }' >> /etc/nginx/nginx.conf
echo '}' >> /etc/nginx/nginx.conf
