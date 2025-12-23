# Stage 1: Build the Angular app
FROM node:18 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration=production

# Stage 2: Serve with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/ebanking-frontend/browser /usr/share/nginx/html
# Copy custom nginx config (we will create this in Step 3)
COPY nginx.conf /etc/nginx/templates/default.conf.template
EXPOSE 80