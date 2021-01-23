#!/bin/bash

corsUrls=${CORS//,/ }
line=$(more /etc/nginx/nginx.conf | wc -l)
let line=line-1
for corsUrl in $corsUrls; do
    pathArray=(${corsUrl//\// })
    index=0
    location="\\    location "
    for  pathItem in ${pathArray[@]}; do
        if [[ $index != 0 && $index != 1 ]]; then
           location="$location/$pathItem {"
        fi
        let index++
    done

    existProxy=$(cat /etc/nginx/nginx.conf | grep "$location")
    if [[ $existProxy ]]; then
      echo "existed -> $location" >> /cors-existed.txt
      echo "existed -> \\      proxy_pass ${pathArray[0]}//${pathArray[1]};" >> /cors-existed.txt
      echo "existed -> \\    }" >> /cors-existed.txt
    else
      sed -i "${line}i $location" /etc/nginx/nginx.conf
      echo "${line}i $location" >> /cors-configured.txt
      let line++
      sed -i "${line}i \\      proxy_pass ${pathArray[0]}//${pathArray[1]};" /etc/nginx/nginx.conf
      echo "${line}i \\      proxy_pass ${pathArray[0]}//${pathArray[1]};" >> /cors-configured.txt
      let line++
      sed -i "${line}i \\    }" /etc/nginx/nginx.conf
      echo "${line}i \\    }" >> /cors-configured.txt
      let line++
    fi
done

if [[ $ACCESS_URL ]]; then
  accessUrlConfigured=$(cat $SWAGGER_JSON | grep "$ACCESS_URL")
  if [[ $accessUrlConfigured ]]; then
    echo "$accessUrlConfigured existed in $SWAGGER_JSON" >> /skip-conf-access-url.txt
  else
    sed -i "s#- url:.*#- url: '$ACCESS_URL'#" $SWAGGER_JSON
    echo "conf $ACCESS_URL to $SWAGGER_JSON" >> /access-url-configured.txt
  fi
fi

