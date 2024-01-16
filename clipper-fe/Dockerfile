# Stage 1: Build the React.js application
FROM node:20-alpine as build-stage
WORKDIR /clipper-fe

# Copy package.json and package-lock.json (if exists)
COPY package*.json ./
COPY tsconfig.json ./
COPY postcss.config.js ./
#env gets injected by kubernetes
#COPY .env ./

# Install dependencies and build the app
RUN npm install --ignore-scripts
COPY src/ ./src
COPY public/ ./public
RUN npm run build

# Stage 2: Serve the React application from Nginx
FROM nginx:1.25.2-alpine
#Conf for nginx
COPY nginx.conf /etc/nginx/nginx.conf
# Copy the built React application from the build stage to Nginx's HTML directory
COPY --from=build-stage /clipper-fe/build /usr/share/nginx/html

# Nginx will automatically serve the React app on port 80

