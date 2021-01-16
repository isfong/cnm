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
    sed -i "${line}i $location" /etc/nginx/nginx.conf
    let line++
    sed -i "${line}i \\      proxy_pass ${pathArray[0]}//${pathArray[1]};" /etc/nginx/nginx.conf
    let line++
    sed -i "${line}i \\    }" /etc/nginx/nginx.conf
    let line++
done

echo 'cors ran' >> /cors-ran.txt