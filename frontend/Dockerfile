FROM node:lts-alpine as build-stage
WORKDIR /app
ENV HOST=0.0.0.0
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
CMD npm run dev