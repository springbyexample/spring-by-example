DESCRIPTION

Greetings,
Current directory contains sample DEMO application built for Android platform.
Target OS is 4.0 (aka Jelly Bean).

The example demonstrates simple client application using JSON for server communication.
Recently the application was rewritten to use Google best practices - SyncAdapter pattern.

Application contains:
- Simple UI to perform basic CRUD operations;
- Account built in
- SyncAdapter for all network operations
- ContentProvider based DAO

HOWTO USE

To run the app you'll have to setup and run corresponding server. Below are the steps to succeed:

1. Open server codebase located in ./spring-by-example-2

2. run maven command: mvn jetty:run
All the dependencies will be downloaded and project will be built. Web server will be launched on port 8080, make sure to have it free prior to launch.

3. Install the Android client on your device and navigate to settings from main Activity. Please, provide there IP of you server.

4. Have fun!
