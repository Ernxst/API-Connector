with open("src/main/resources/application.properties", "r") as file:
    all_lines = [line for line in file]
    lines = [line for line in all_lines if line.startswith("server.port")]
    DEFAULT_PORT = lines[0].split("=")[1]

if __name__ == "__main__":
    import argparse
    from os import system

    parser = argparse.ArgumentParser(prog="API Connector", description='Run the application as a RESTful API service on localhost.')
    parser.add_argument("-port", "--port", "-p", "--p", type=str, default=DEFAULT_PORT, nargs="?", 
                    help='(optional) choose to run on a desired port - default is ' + DEFAULT_PORT)
    parser.add_argument('-l', '--l', action="store_true", help='include logging information during execution')
    parser.add_argument('-jar', '--jar', action="store_true", help="build application into a single JAR file.")
    args = parser.parse_args()

    log = " l" if args.l else ""
    if (args.jar):
        cmd = "set -e; mvn clean package; java -jar target/API-Connector-1.0-SNAPSHOT.jar port={PORT}{LOG}"
    else:
        cmd = "set -e; mvn clean; mvn org.springframework.boot:spring-boot-maven-com.example.plugin:run -Dspring-boot.run.arguments=\"--port={PORT}{LOG}\""
    system(cmd.format(LOG=log, PORT=args.port))