CREATE TABLE category (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE ingredient (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE recipe (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  instructions TEXT,
  category_id INTEGER REFERENCES category(id)
);

CREATE TABLE recipe_ingredient (
  recipe_id INTEGER REFERENCES recipe(id),
  ingredient_id INTEGER REFERENCES ingredient(id),
  PRIMARY KEY (recipe_id, ingredient_id)
);
