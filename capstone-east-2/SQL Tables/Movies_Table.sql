CREATE TABLE IF NOT EXISTS Movies
(
  id SERIAL UNIQUE NOT NULL,
  title VARCHAR(120) NOT NULL,
  category VARCHAR (120) NOT NULL,
  description VARCHAR (255),
  PRIMARY KEY (id)
);
