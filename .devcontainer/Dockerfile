FROM maven:3.9.12-eclipse-temurin-21

# Install SSH and other tools
RUN ls -la /root && \
    chmod 777 /root && \
    apt-get update && apt-get install -y \
    openssh-client \
    openssl \
    unzip \
    curl \
    git \
    && rm -rf /var/lib/apt/lists/*

# Install mvnd (optional, if you’re using Maven Daemon)
ENV MVND_VERSION=1.0.2
RUN curl -L https://downloads.apache.org/maven/mvnd/${MVND_VERSION}/maven-mvnd-${MVND_VERSION}-linux-amd64.zip -o mvnd.zip && \
    unzip mvnd.zip && \
    ls && \
    mv maven-mvnd-${MVND_VERSION}-linux-amd64 /opt/mvnd && \
    ln -s /opt/mvnd/bin/mvnd /usr/local/bin/mvnd && \
    rm mvnd.zip && \
    git config --global --add safe.directory /IdeaProjects/tournament-planner

# Set up a non-root user
#RUN useradd -ms /bin/bash vscode && chown -R vscode /home/vscode
#USER vscode

ENV PATH="/opt/mvnd/bin:$PATH"

EXPOSE 8080
