FROM docker-registry.tools.expedia.com/stratus/primer-base-springboot:8-2
STOPSIGNAL SIGWINCH

ENV APP_NAME=package-deals \
    JAVA_DEBUG=false
# Add custom entrypoint script
COPY docker-entrypoint.sh /

# Install application
COPY target/package-deals-0.1.0.jar /app/bin/
