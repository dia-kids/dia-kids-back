CREATE TABLE IF NOT EXISTS articles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    published_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    text TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS pictures (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    article_id INT,
    FOREIGN KEY (article_id) REFERENCES articles(id),
    user_id INT,
    posted_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    text TEXT NOT NULL
);