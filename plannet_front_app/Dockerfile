FROM node:20.11.0
WORKDIR /app
COPY plannet_front_app/ ./
RUN npm ci
COPY . .
RUN npm run build
RUN npm install -g serve
EXPOSE 5173
CMD ["serve", "dist", "-l", "5173"]