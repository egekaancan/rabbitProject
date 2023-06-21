CREATE TABLE IF NOT EXISTS pizza (
                                     id SERIAL PRIMARY KEY,
                                     pizza_name VARCHAR(255)
    );

-- Create the Ingredient table if it doesn't exist
CREATE TABLE IF NOT EXISTS ingredient (
                                          id SERIAL PRIMARY KEY,
                                          ingredient_name VARCHAR(255)
    );

-- Create the join table for the many-to-many relationship if it doesn't exist
CREATE TABLE IF NOT EXISTS ingredient_list (
    pizza_id BIGINT REFERENCES pizza(id),
    ingredient_id BIGINT REFERENCES ingredient(id),
    PRIMARY KEY (pizza_id, ingredient_id)
    );