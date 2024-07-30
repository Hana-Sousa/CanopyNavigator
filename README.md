---

# CanopyNavigator

CanopyNavigator is a Spring Boot application designed to assist with forest inventory and management in the Amazon Rainforest. 
This application allows users to visualize geospatial data, handle CSV file uploads, convert them to Shapefiles, and create maps with various functionalities.

## Features

- Upload CSV files and convert them to Shapefiles
- Visualize geospatial data
- Create maps with regions and prohibited areas
- Generate pathways based on prohibitions

## Technologies Used

- Java
- Spring Boot
- GeoTools
- JTS
- PostgreSQL
- PostGIS

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- PostgreSQL with PostGIS extension

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/Hana-Sousa/CanopyNavigator.git
    cd CanopyNavigator
    ```

2. Set up PostgreSQL and PostGIS:
    - Install PostgreSQL and PostGIS.
    - Create a database and enable the PostGIS extension.

3. Configure `application.properties`:
    - Update `src/main/resources/application.properties` with your PostgreSQL credentials and other configuration settings.

4. Build and run the application:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Usage

### API Endpoints

- `POST /upload`: Upload a CSV file
- `GET /features`: Retrieve all geospatial features
- `POST /features`: Create a new geospatial feature

### Example Requests

**Upload CSV:**

```bash
curl -X POST -F 'file=@path_to_your_file.csv' http://localhost:8080/upload
```

**Get All Features:**

```bash
curl http://localhost:8080/features
```

---
