# Master Person Index (dohMPI) which aids with de-duplication and maintaining consistent ID for its data sources; such as the death information system, EDEN

CS6440  Fall 2017  Course Project          11/29/17

Team:  SPYNdoctors  ( Nargis Bisset,  Arjun Puri, Hernando Salas, and Alex Yanovsky)

Mentors:  Dr. Kailah Davis,  Dr. Jeffrey Duncan, Utah Department of Health

Project TA: Paul  Miller

Github link:   [https://github.com/CDCgov/GaTech-Fall2017-Davis-dohMPI-SPYNdoctors](https://github.com/CDCgov/GaTech-Fall2017-Davis-dohMPI-SPYNdoctors)

# Special Instructions

The project should be executed in the &quot;Final Delivery&quot; folder. It contains

 docker-compose.yml  which will be using the &quot;Final Delivery/testdata/edenpopulate.sql&quot; file when executing docker-compose up.

If executing in the environment where X-Windows server is accessible, please issue:

_xhost  +_

command prior to executing:

_docker-compose up_

Docker-compose should pull four images from hub.docker.com and run four containers (see Manual, section Submission for grading for detailed description). One of the images by request of the mentors resides in private repository, djonson325 has access to it; please, contact us if additional collaborators need to be added).

The application can be tested using either browser or provided standalone Java application client. The browser should be pointed at:

_http://localhost:8888/DohMPI\_EDEN/_

for testing POST method. For testing GET the following URL should be used:

_http://localhost:8888/DohMPI\_EDEN/rest/match/first\_name/{first\_name}/last\_name/{last\_name}/gender/{gender}/date\_of\_birth/{dob}_

When testing GET method with the browser, date of birth should be input without slashes:, e.g. 06221969

In case docker-compose is run under X-Windows environment, the client container will launch the application and application window should appear on the screen.

 If X-Windows server is not accessible, then application window will not show up automatically; in this case the client can be started manually from the host prompt, using DohMPI\_EDEN\_Client.jar residing in &quot;Final Delivery&quot; folder by issuing the command on the host terminal:

_  java -jar DohMPI\_EDEN\_Client.jar_ [_http://localhost:8888/DohMPI\_EDEN/rest_](http://localhost:8888/DohMPI_EDEN/rest)

For testing GET method with standalone client, just select GET tab. Date of birth for the client should always be supplied with slashes, because the client application will take care of removing slashes before submitting request to GET endpoint.

When running &quot;docker-compose up&quot;, please start testing with either web or application client only after the server is up and running (will show the message &quot;Server started in XXXX msec&quot; or will stop showing output in &quot;docker-compose up&quot; window).

These are some examples of input fields for testing the application:

======================  Example 1 ============

First name:  Carrie

Last name White

Date of birth: 06/22/1969

Gender: F

=====

Output:   DohMPI: Match successful; EDEN: Match successful  (shows immediate cause of death)

======================  Example 2 ============

First name:  Dme

Last name: Test

Date of birth: 05/03/2015

Gender: M

=====

 Output: DohMPI: Match successful; EDEN: Match successful  (Date of death 05/19/2015; no immediate cause of death shown)

======================  Example 3 ============

First name:  EARLY

Last name: DAYS

Date of birth: 09/09/2015

Gender: F

=====

Output: DohMPI: Match successful; EDEN: No StateFileNumber

=======================End of Special Instructions ==========