# stats-service
A **Grails (Groovy)**-based microservice that tracks and displays statistics related to **books** and **users** by consuming Kafka messages from other services in the system.
This service listens to events (e.g., user registrations, book creations) via **Kafka**, processes the data, and maintains aggregate stats for analytics or display.

## Overview

- Built with **Grails** and **Groovy**
- Listens to Kafka topics for **user** and **book** events
- Updates internal state/stats based on consumed messages
- Designed to be part of a broader microservices ecosystem

## Tech Stack

- Grails 6.2.3 (Groovy on Spring Boot)
- JVM Version: 17.0.14
- Apache Kafka
- Spring Kafka
- Gradle
- TimescaleDB (InfluxDB)
- Spock for tests
- Nginx
  
```
upstream stats_service {
        server 127.0.0.1:8004;
    }
```

```
location /stats-service {
        # Don't rewrite for assets paths	
	#if ($request_uri !~* ^/stats-service/assets/) {
         #   rewrite ^/stats-service(/.*)$ $1 break;
        #}

	# when using default grails
	rewrite ^/stats-service(/.*)$ $1 break;
	
        proxy_pass http://stats_service/;

	# Added this block to modify HTML responses for default
    	sub_filter '/assets/' '/stats-service/assets/';
    	sub_filter_once off;
    	sub_filter_types text/html;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;

        proxy_connect_timeout 600;
        proxy_send_timeout 600;
        proxy_read_timeout 600;
        send_timeout 600;
    }

    # Handle asset requests specifically
    location ~* ^/stats-service/assets/(.*) {
        # Pass to the Grails app server without stripping the /stats-service prefix
        proxy_pass http://stats_service/assets/$1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        access_log off;
        expires max;
        add_header Cache-Control "public, max-age=31536000";
    }
```

> ⚠️ **WIP**:
> - Dockerisation
