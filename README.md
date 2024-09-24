
# SCANNER APPLICATION

---

## Prerequisites:
- Docker
- Docker Compose (if applicable)

---

## Description:
- The Scanner application provides APIs for handling repository scans for clients.
- To ensure scalability, the application saves scan requests in a database. Then, these requests are written to files on the hard drive using Watchdog.
- Watchdog is scheduled to process the files in the order of their creation and runs scans accordingly.
- If a scan is successful, the corresponding file is deleted. If a scan fails, the file remains and will be retried in the next round.

---

## Instructions:

1. Download the Postman collection from the "resources" package.
2. While attempting to run the application in a Docker container, I encountered network connectivity issues between containers. Therefore, you need to run this application using IntelliJ.
3. Update the FILE-PATH in the class `WatchdogFileService` based on your operating system.

---

## Running the MySQL Database Container:

1. Open a terminal in the root directory of the project.
2. Run the following command to start the MySQL container:
   ```bash
   docker-compose up -d
   ```

---

# Backend Architecture

![img.png](img.png)

---
