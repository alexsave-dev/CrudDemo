# Purpose
This is the demo application to cover areas like Spring Boot, Postgres DB, Spring controller-service-repository approach, crud operations and Spring boot tests.

## Getting started
Install the docker service on your machine.

From the command prompt/console go to the directory crud_demo in project root and then run the command:
docker-compose up -d

The container for postgres will be up and running as a result.

Start the CrudDemoApplication.

## API
**GET /tasks/{id}** - displays details of the task with given ID.

**GET /tasks** - displays details of all tasks.

**POST /tasks** - writes the task to DB. The example of JSON body for the call:
```json
{
  "title": "title",
  "description": null,
  "dueDate": null,
  "completed": null
}
```

**PUT /tasks/{id}** - updates the task with then given ID in DB.
```json
{
  "id": 2,
  "title": "title",
  "description": null,
  "dueDate": null,
  "completed": null
}
```

**DELETE /tasks/{id}** - deletes the task with the given ID from DB.
 


