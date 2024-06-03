
FROM node:18-alpine

WORKDIR /app

EXPOSE 5300

COPY package.json package-lock.json ./

RUN npm install --silent

COPY . /app

CMD ["npm", "run", "dev"]