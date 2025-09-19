.PHONY: build test run clean deploy help

help: ## Show this help
	@egrep -h '\s##\s' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

build: clean ## Build all modules
	./gradlew build

test: clean ## Run all tests
	./gradlew test

run: clean ## Run the main application
	./gradlew :main-app:bootRun

clean: ## Clean all build artifacts
	./gradlew clean

deploy: ## Deploy to environment
	# TODO: Add deployment commands here

