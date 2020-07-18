# MAKEFILE https://github.com/tstelzle/ShipTerminator
# AUTHORS: Tarek Stelzle

IMAGE-NAME := java-env-ship_terminator
CONTAINER-NAME-1 := shipTerminator-1
CONTAINER-NAME-2 := shipTerminator-2
WORKING-DIR := $(PWD)/src
BRANCH := $(shell git rev-parse --abbrev-ref HEAD)
IGNORE-OUTPUT := > /dev/null 2>&1

define build
	docker exec -it $(1) javac control/ConsoleDirectController.java
	docker exec -it $(1) java control/ConsoleDirectController
endef

default:
	@echo "Possible Targets:"
	@echo " build-image 		- Builds the image."
	@echo " containers		- Initializes two containers two play the game."
	@echo " run-1 			- Runs the game in the first container."
	@echo " run-2 			- Runs the game in the second container."
	@echo " run-master-1 		- Runs the game int the first conatiner on the master branch."
	@echo " run-master-2 		- Runs the game int the second conatiner on the master branch."
	@echo " terminal-1 		- Opens a terminal in the first container."
	@echo " terminal-2 		- Opens a terminal in the second container."
	@echo " ip-1 			- Prinst the ip of the first container."
	@echo " ip-2 			- Prinst the ip of the second container."


build-image:
	docker build -t $(IMAGE-NAME) .

containers:
	docker run -d -t --rm -v $(WORKING-DIR):/usr/src/myapp --name $(CONTAINER-NAME-1) $(IMAGE-NAME)
	docker run -d -t --rm -v $(WORKING-DIR):/usr/src/myapp --name $(CONTAINER-NAME-2) $(IMAGE-NAME)

run-1: ip-1
	$(call build, $(CONTAINER-NAME-1))

run-2: ip-2
	$(call build, $(CONTAINER-NAME-2))

run-master-1:
	git stash --include-untracked $(IGNORE-OUTPUT)
	git checkout master $(IGNORE-OUTPUT)
	$(call build, $(CONTAINER-NAME-1))
	git checkout $(BRANCH) $(IGNORE-OUTPUT)
	git stash pop $(IGNORE-OUTPUT)

run-master-2:
	git stash --include-untracked $(IGNORE-OUTPUT)
	git checkout master $(IGNORE-OUTPUT)
	$(call build, $(CONTAINER-NAME-2))
	git checkout $(BRANCH) $(IGNORE-OUTPUT)
	git stash pop $(IGNORE-OUTPUT)

terminal-1:
	docker exec -it $(CONTAINER-NAME-1) /bin/bas

terminal-2:
	docker exec -it $(CONTAINER-NAME-2) /bin/bash

ip-1:
	docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(CONTAINER-NAME-1)

ip-2:
	docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' $(CONTAINER-NAME-2)

