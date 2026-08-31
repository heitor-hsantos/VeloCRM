FROM node:20-alpine

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy source code
COPY . .

EXPOSE 3000

# For a dev environment, simple npm run dev is enough
CMD ["npm", "run", "dev"]
