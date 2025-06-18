# build
FROM node:20 AS build

WORKDIR /app
COPY package*.json ./
COPY ../.env.production .env.production
RUN npm install

COPY ../ ./
RUN npm run build

# nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]