FROM swaggerapi/swagger-ui:v3.41.1
RUN sed -ie 's#http://dl-cdn.alpinelinux.org#https://mirrors.aliyun.com#g' /etc/apk/repositories
RUN apk update && apk add bash
ADD cors.sh /bin/
ADD run.sh /usr/share/nginx/
ENV ACCESS_URL ""
# docker build -t registry.cn-guangzhou.aliyuncs.com/isfong/cors-swagger-ui:v3.41.1 .