start:
	docker compose up -d
erase:
	docker compose down --rmi all -v
stop:
	docker compose down
spring-container:
	docker exec -it spring_container /bin/bash
postgres-logs:
	docker logs postgres_container
postgres-container:
	docker exec -it postgres_container /bin/bash
spring-logs:
	docker logs spring_container
post:
	curl --header "Content-Type: application/json" \
      --request POST \
      --data '{"nome": "Auxilio Moradia","valor_percentual": 50,"contribuicao_minima": 2,"individual": true}' \
      -i http://localhost:8082/beneficios
	curl --header "Content-Type: application/json" \
	  --request POST \
	  --data '{"nome": "Auxilio Comida","valor_percentual": 80,"contribuicao_minima": 12,"individual": true}' \
	  -i http://localhost:8082/beneficios
	curl --header "Content-Type: application/json" \
	  --request POST \
	  --data '{"nome": "Auxilio Gas","valor_percentual": 12,"contribuicao_minima": 1,"individual": true}' \
	  -i http://localhost:8082/beneficios
	curl --header "Content-Type: application/json" \
	  --request POST \
	  --data '{"nome": "Pensao por morte","valor_percentual": 54,"contribuicao_minima": 120,"individual": false}' \
	  -i http://localhost:8082/beneficios
	curl --header "Content-Type: application/json" \
	  --request POST \
	  --data '{"nome": "Auxilio Familia","valor_percentual": 25,"contribuicao_minima": 36,"individual": false}' \
	  -i http://localhost:8082/beneficios
get:
	curl -d "cpf=99999999994&beneficio_id=1" -i http://localhost:8082/concessao/individual
	curl -d "cpf=99999999993&beneficio_id=2" -i http://localhost:8082/concessao/individual
	curl -d "cpfRequisitante=99999999994&beneficio_id=4&cpfBeneficiado=99999999993" -i http://localhost:8082/concessao
env:
	echo "POSTGRES_USER=\nPOSTGRES_PASSWORD=\nPGPORT=" > .env




