FROM postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB marsroverdb
#COPY init.sql /docker-entrypoint-initdb.d/