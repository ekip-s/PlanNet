# Базовый образ (Alpine - легковесный вариант)
FROM nginx:1.23-alpine

# Удаляем дефолтный конфиг
RUN rm /etc/nginx/conf.d/default.conf

# Копируем кастомный конфиг
COPY nginx.conf /etc/nginx/nginx.conf

# Создаем директории для Certbot
RUN mkdir -p /var/www/certbot && \
    chown -R nginx:nginx /var/www/certbot && \
    chmod -R 755 /var/www/certbot

# Устанавливаем права для логов
RUN chown -R nginx:nginx /var/log/nginx && \
    chmod -R 755 /var/log/nginx

# Открываем порты
EXPOSE 80 443

# Запуск NGINX
CMD ["nginx", "-g", "daemon off;"]