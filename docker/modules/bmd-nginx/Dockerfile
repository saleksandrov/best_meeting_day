FROM nginx

RUN rm /etc/nginx/conf.d/default.conf

COPY ./docker/modules/bmd-nginx/bmd-nginx.conf /etc/nginx/conf.d

COPY ./bmd-base/src/main/resources/ui/build/ /www/data

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]