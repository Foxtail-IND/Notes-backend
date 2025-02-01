FROM maven:3.9.6

WORKDIR /app

# Copy only the pom.xml first to leverage Docker caching
COPY pom.xml ./

# Generate Maven wrapper
RUN mvn wrapper:wrapper
RUN ls -l /app
RUN cat /app/mvnw
RUN chmod +x mvnw mvnw.cmd

# Copy the rest of the application files
COPY . . 

# Build the project
RUN ./mvnw clean package

# Run the application
CMD ["./mvnw", "spring-boot:run"]
