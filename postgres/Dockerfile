FROM postgres:13.1

RUN sed -i 's#deb.debian.org#mirrors.aliyun.com#g' /etc/apt/sources.list
RUN sed -i 's#security.debian.org#mirrors.aliyun.com#g' /etc/apt/sources.list
RUN apt-get update && apt-get -y install git build-essential postgresql-server-dev-13
RUN git clone https://gitee.com/isfong/wal2json.git -b master --single-branch && cd /wal2json && make && make install && cd / && rm -rf wal2json && apt-get clean && rm -rf /var/lib/apt/lists/*
ADD additional-scripts /
ADD docker-entrypoint-initdb.d/* /docker-entrypoint-initdb.d/
COPY postgresql.conf.sample /usr/share/postgresql/postgresql.conf.sample