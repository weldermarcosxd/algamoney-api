CREATE TABLE `algamoney`.`pessoa` (
  `id` BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `active` INT(01) NULL NULL,
  `street` VARCHAR(60),
  `number` INT(06),
  `complement` VARCHAR(30),
  `neighborhood` VARCHAR(30),
  `cep` INT(08),
  `city` VARCHAR(50),
  `state` VARCHAR(50))
ENGINE = InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("alpha 1",1,"alpha street",1,null,null,1111,"alphaville","alpha");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("beta 1",1,"beta street",1,null,null,2222,"betaville","beta");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("gama 1",1,"gama street",1,null,null,3333,"gamaville","gama");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("delta 1",1,"delta street",1,null,null,44444,"deltaville","delta");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("epsilion 1",1,"epsilion street",1,null,null,5555,"epsilionville","epsilion");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("zeta 1",0,"zeta street",1,null,null,6666,"zetaville","zeta");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("eta 1",0,"eta street",1,null,null,7777,"etaville","eta");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("teta 1",0,"teta street",1,null,null,8888,"tetaville","teta");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("iota 1",0,"iota street",1,null,null,9999,"iotaville","iota");
INSERT INTO `algamoney`.`pessoa`(`name`,`active`,`street`,`number`,`complement`,`neighborhood`,`cep`,`city`,`state`)VALUES("capa 1",0,"capa street",1,null,null,0000,"capaville","capa");