FROM swaggerapi/swagger-ui:v3.39.0
RUN sed -ie 's#http://dl-cdn.alpinelinux.org#https://mirrors.aliyun.com#g' /etc/apk/repositories
RUN apk update && apk add bash
ADD cors.sh /bin/
RUN /bin/cors.sh
# docker build registry.cn-shenzhen.aliyuncs.com/isfong/cors-swagger-ui:v3.39.0 .