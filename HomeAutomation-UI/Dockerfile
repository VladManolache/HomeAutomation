FROM nginx:alpine

## Add the default configuration for Angular app
COPY nginx/default.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## Copy the dist result
COPY dist/HomeAutomation/ /usr/share/nginx/html

