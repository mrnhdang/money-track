# money-track
Run PostgresSQL:
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -d postgres
Run PgAdmin:
docker run --name my-pgadmin -p 82:80 -e PGADMIN_DEFAULT_EMAIL=laiba@yahoo.com -e PGADMIN_DEFAULT_PASSWORD=pass123 -d dpage/pgadmin4
Have to run postgrsql first 
# note
We can use this line of code to get data from SecurityContextHolder:
--         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();




#Validation Rule (throw varieties of Exception -- RunTimeException)
-- Create Validation Exception class to extend RunTimeException
-- Service class.validate ---> (call to validator) getBussinessScenerio to return class Validation Rule ---> (Validate) For each class validation rule in throw ValidationException

* Note:
- So ValidationException is a common RuntimeException to throw validation rule error. (Create ValidationException.java)
- Create an array of ValidationError to add an object of format error message (error code, error message) (Handle in ValidationRule class)--(ValidationError addError())
- Loop through all ValidationRule class and throw ValidationException if contain error (handle in Validator.class)