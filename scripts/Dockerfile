FROM node:10.15-alpine
RUN mkdir /app
WORKDIR /app
COPY package.json /app
COPY package-lock.json /app
RUN npm install
COPY .env /app
COPY getUSDX.js /app
