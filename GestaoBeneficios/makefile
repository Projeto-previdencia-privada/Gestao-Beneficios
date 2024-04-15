start:
	docker compose up -d
erase:
	docker compose down -rmi all -v
post:
	curl --header "Content-Type: application/json" \
      --request POST \
      --data '{"nome": "Auxilio Moradia","valor_percentual": 100,"contribuicao_minima": 2,"individual": true}' \
      http://localhost:8082/beneficios