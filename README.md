# money-track
Run PostgresSQL:
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -d postgres
Run PgAdmin:
docker run --name my-pgadmin -p 82:80 -e PGADMIN_DEFAULT_EMAIL=laiba@yahoo.com -e PGADMIN_DEFAULT_PASSWORD=pass123 -d dpage/pgadmin4
Have to run postgrsql first 