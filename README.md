
# Conference Room Booking Service


## Table of Contents


1. [Project Description](#project-description)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Configuration](#configuration)
5. [Contributing](#contributing)



## Project Description

Service is used to book the configured conference room by receiving the input as
1. Person(s) capacity
2. Start date time
3. End date time

At Present, 4 Rooms are Configured in the system .

| Name          | Capacity (Persons)   |
|---------------|----------------------|
| Amaze         | 3                    |
| Beauty        | 7                    |
| Inspire       | 12                   |
| Strive        | 20                   |

Each Room has a maintenance time of below
Maintenance Time : Room would be serviced and is not allowed to book / open to booking during maintenance times

1. 9:00 - 9:15
2. 13:00 - 13:15
3. 17:00 - 17: 15

### Project Requirements
1. Booking can be done only for today and no future date
2. Booking can be done in interval of 15 minutes i.e 15/30/45/60 minutes
3. Booking not permitted during maintenance times .


## Installation

Instructions on how to install and set up the project. Include any prerequisites, such as Java version, Maven, etc.

### Prerequisites

- Java 17 or above
- Maven

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/your-project.git
   ```

2. Navigate to the project directory:

   ```bash
   cd your-project
   ```

3. Build the project:

   ```bash
   mvn clean install
   ```

## Usage

Instructions on how to use the project. Include examples, command-line options, and any other relevant information.

### Example

```bash
mvn spring-boot:run 
```
Api s would be available to test under http://localhost:8080/booking-system/swagger-ui/index.html


### Configuration File

Application is available under below configured port and context-path
```
server:
  port: 8080
  servlet:
    context-path: /booking-system
```

Change the below urls if you want to change swagger parameters
```
springdoc:
    swagger-ui:
        enabled: true
        disable-swagger-default-url: true
        config-url: /booking-system/v3/api-docs/swagger-config
        url: /booking-system/v3/api-docs    
```
To Change the Minimum required time of the booking , please change below parameters , 
#### Note: parameter is in minutes
 
```
app:
  minimumBookingInterval: 15
```
Explain how to configure the project using a configuration file.

### Bug Reports
Please report any bugs or issues by opening an issue on the GitHub repository.

### Feature Requests
Please submit any feature requests by opening an issue on the GitHub repository.

### Contributing Code

1. Fork the repository.
2. Create a new branch: `git checkout -b feature-branch`
3. Make your changes and commit them: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature-branch`
5. Submit a pull request.


---

