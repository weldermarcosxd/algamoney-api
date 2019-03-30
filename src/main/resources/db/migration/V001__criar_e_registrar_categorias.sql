CREATE TABLE `algamoney`.`categoria` (
  `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL)
ENGINE = InnoDB DEFAULT CHARSET=utf8;

Insert into categoria (name) values ('Lazer');
Insert into categoria (name) values ('Alimentação');
Insert into categoria (name) values ('Supermercado');
Insert into categoria (name) values ('FArmácia');
Insert into categoria (name) values ('Outros');
