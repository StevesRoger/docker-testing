FROM node:18
ARG PROXY_AGENT_ARG=http://161.117.89.36:8888
ENV PROXY_AGENT=${PROXY_AGENT_ARG}
WORKDIR /usr/src/app
LABEL maintainer=kimchheng email=kimchheng101@gmail.com
COPY ["package.json", "package-lock.json*", "./"]
RUN npm install
# If you are building your code for production
# RUN npm ci --omit=dev
COPY . .
EXPOSE 3000
CMD npm start