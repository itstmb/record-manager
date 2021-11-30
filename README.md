# record-manager
RESTful web service to create, read/list, and delete records within a hierarchy/tree.

## Running the code
1. Navigate to the project directory, then run the docker-compose file to set up your MySQL requirements
```
docker-compose up -d
```
2. Run maven to download all dependencies and build the project
```
mvn clean install
```
3. Run the spring boot application
```
mvn spring-boot:run
```

## Endpoints

### `GET /api/record/`
Request params: 
* (Optional) (String) parent

example request: http://localhost:8080/api/record/?parent=record

### `PUT /api/record/`
Request params: 
* (List<String>) records
* (Optional)(String) parent 

example request: http://localhost:8080/api/record/parent=parent_record&records=child1,child2

### `DELETE /api/record/`
Request params: 
* (Optional) (String) parent 

example request: http://localhost:8080/api/record/?parent=parent_record 

**Note**: not including a parent deletes the root node, which will recursively delete all other nodes.

--------------------------------------------------------------------------------
