FROM node:lts-alpine as build-stage
WORKDIR /app
ENV HOST=0.0.0.0
COPY package*.json ./
COPY ./entrypoint.sh /entrypoint.sh
RUN npm install
COPY . .
RUN npm run build && chmod 777 /entrypoint.sh
#ENTRYPOINT /entrypoint.sh
CMD /bin/sh /entrypoint.sh && npx quasar dev