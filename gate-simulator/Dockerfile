FROM node:latest
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY .. .
EXPOSE 9999
CMD [ "node", "app.js" ]