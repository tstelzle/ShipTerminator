# Ship Terminator

## About
This is an implementation of the common game ship terminator. 
Two players play against each other. Each of them has a board in front of them, onto where they can place different ships.
After every player put their ships on the board. 
The players shoot alternating onto the board of the opponent.
The goal of the game is to destroy alle the ships of the opponent.

## Explanation Of The Implementation
This game is console based. 
First of all you have to decide whether you are hosting (1-senden) or waiting for a host (2-warten).
The hosting person needs to know his and the opponents ip.

Then the players can follow the guide for placing their ships onto the board.

After this the game asks for the coordinates of the next shot.

The players always see to boards. 
The upper one is theirs and the lower one is the opponents board.

The numbers in the board have the following semantic:
* 0: No information
* 1: Ship
* 2: Missed attack
* 3: Hit

## Running
### Docker
With the Makefile you can build yourself two containers and run them both on your machine. Therefore you can play against each other only with one computer. This is good for development. With this setup you need to insert, when asked for ip adresses, the ip addresses of the docker containers. These will be printed when you run the commands 'make run-1' and 'make run-2'.

Follow these steps to run the application:
1. make build-image
2. make containers
3. make run-1
4. make run-2

If you want to play with docker on two machines each of them has to follow the steps above. The last step can be abondend. 
When the game asks for the ip addresses you have to input the ip of your machine and not of the docker container.
### Local
For running the application withouth docker you need to install java and compile 'control/ConsoleDirectController.java'. Then you can run the application with 'java control/ConsoleDirectController'.
As the upd port is used you can only play against each other with two different machines in the same network.
You need to know the ip addresses of the machines.
