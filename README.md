# lintick_inventario

1. Crear Inventario 
	endpoint: http://localhost:8080/inventario/crear
	CURL: curl --request POST http://localhost:8080/inventario/crear -H "Content-Type: application/vnd.api+json" -d '{"cantidad":5,"id_producto":1}' 

2. Actualizar Inventario 
	endpoint: http://localhost:8080/inventario/actualizar
	CURL: curl --request POST http://localhost:8080/inventario/actualizar -H "Content-Type: application/vnd.api+json" -d '{"cantidad":5,"id_producto":1}'

3. Consultar Cantidad de producto por ID 
	endpoint: http://localhost:8080/inventario/cantidad/{id}
	CURL: curl --request GET http://localhost:8080/inventario/cantidad/{id} -H "Content-Type: application/vnd.api+json"

4. Comprar Producto 
	endpoint: http://localhost:8080/inventario/comprar
	CURL: curl --request POST http://localhost:8080/inventario/comprar -H "Content-Type: application/vnd.api+json" -d '{"cantidad":2,"id_producto":1}'