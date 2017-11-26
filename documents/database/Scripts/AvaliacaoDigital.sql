-- MySQL Script generated by MySQL Workbench
-- Tue Nov 21 09:19:21 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema avaliacaoDigital
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema avaliacaoDigital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `avaliacaoDigital` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `avaliacaoDigital` ;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`professor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`professor` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`modulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`modulo` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`turma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`turma` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `professorCodigo` INT UNSIGNED NOT NULL,
  `moduloCodigo` INT UNSIGNED NOT NULL,
  `inicio` DATETIME NOT NULL,
  `fim` DATETIME NOT NULL,
  `descricao` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC),
  INDEX `codigo_idx` (`professorCodigo` ASC),
  INDEX `codigo_idx1` (`moduloCodigo` ASC),
  CONSTRAINT `fkProfessorCodigo`
    FOREIGN KEY (`professorCodigo`)
    REFERENCES `avaliacaoDigital`.`professor` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkModuloCodigo`
    FOREIGN KEY (`moduloCodigo`)
    REFERENCES `avaliacaoDigital`.`modulo` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`usuario` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `acesso` DATETIME NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `administrador` TINYINT NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`aluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`aluno` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `usuarioCodigo` INT UNSIGNED NOT NULL,
  `cpf` BIGINT(11) UNSIGNED NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `eMail` VARCHAR(50) NOT NULL,
  `matricula` INT NOT NULL,
  `nomeMae` VARCHAR(50) NOT NULL,
  `dtNascimento` DATE NOT NULL,
  `carteiraIdentidade` BIGINT(11) NOT NULL,
  `sexo` ENUM('Feminino', 'Masculino') NOT NULL,
  INDEX `fk_aluno_usuario1_idx` (`usuarioCodigo` ASC),
  PRIMARY KEY (`codigo`),
  CONSTRAINT `fk_aluno_usuario1`
    FOREIGN KEY (`usuarioCodigo`)
    REFERENCES `avaliacaoDigital`.`usuario` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`turmaAluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`turmaAluno` (
  `alunoCodigo` INT UNSIGNED NOT NULL,
  `turmaCodigo` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`alunoCodigo`, `turmaCodigo`),
  INDEX `fkTurmaCodigo_idx` (`turmaCodigo` ASC),
  CONSTRAINT `fkTATurmaCodigo`
    FOREIGN KEY (`turmaCodigo`)
    REFERENCES `avaliacaoDigital`.`turma` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkTAAlunoCodigo`
    FOREIGN KEY (`alunoCodigo`)
    REFERENCES `avaliacaoDigital`.`aluno` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`avaliacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`avaliacao` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(50) NULL,
  `objetivo` VARCHAR(50) NOT NULL,
  `inicio` DATETIME NOT NULL,
  `termino` DATETIME NOT NULL,
  `criacao` DATETIME NOT NULL,
  `modificacao` DATETIME NOT NULL,
  `logoPath` VARCHAR(255) NOT NULL,
  `textoConvidativo` VARCHAR(255) NOT NULL,
  `linkPagina` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`questao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`questao` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `texto` VARCHAR(200) NOT NULL,
  `criacao` DATETIME NOT NULL,
  `modificacao` DATETIME NOT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE INDEX `codigo_UNIQUE` (`codigo` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`formulario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`formulario` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `avaliacaoCodigo` INT UNSIGNED NOT NULL,
  `nomeAluno` VARCHAR(50) NULL,
  `matriculaAluno` INT NULL,
  `nomeModulo` VARCHAR(50) NOT NULL,
  `comentariosSugestoes` VARCHAR(500) NULL,
  `turmaCodigo` INT NOT NULL,
  `nomeProfessor` VARCHAR(50) NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_avaliacao_codigo_idx` (`avaliacaoCodigo` ASC),
  CONSTRAINT `fkAvaliacaCoodigo`
    FOREIGN KEY (`avaliacaoCodigo`)
    REFERENCES `avaliacaoDigital`.`avaliacao` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`resposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`resposta` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `formularioCodigo` INT UNSIGNED NOT NULL,
  `resposta` ENUM('Concordo totalmente', 'Concordo', 'Não concordo nem discordo', 'Discordo', 'Discordo Totalmente', 'Não sei avaliar') NOT NULL,
  PRIMARY KEY (`codigo`, `formularioCodigo`),
  INDEX `fkFormularioCodigo_idx` (`formularioCodigo` ASC),
  CONSTRAINT `fkFormularioCodigo`
    FOREIGN KEY (`formularioCodigo`)
    REFERENCES `avaliacaoDigital`.`formulario` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`topico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`topico` (
  `codigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`avaliacaoTopico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`avaliacaoTopico` (
  `avaliacaoCodigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `topicoCodigo` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`avaliacaoCodigo`, `topicoCodigo`),
  INDEX `codigo_idx` (`topicoCodigo` ASC),
  CONSTRAINT `fkTopicoCodigo`
    FOREIGN KEY (`topicoCodigo`)
    REFERENCES `avaliacaoDigital`.`topico` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkAvaliacaoCodigo`
    FOREIGN KEY (`avaliacaoCodigo`)
    REFERENCES `avaliacaoDigital`.`avaliacao` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`topicoQuestao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`topicoQuestao` (
  `topicoCodigo` INT UNSIGNED NOT NULL,
  `questaoCodigo` INT UNSIGNED NOT NULL,
  INDEX `fkQuestaoCodigo_idx` (`questaoCodigo` ASC),
  CONSTRAINT `fkTQTopicoCodigo`
    FOREIGN KEY (`topicoCodigo`)
    REFERENCES `avaliacaoDigital`.`topico` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkTQQuestaoCodigo`
    FOREIGN KEY (`questaoCodigo`)
    REFERENCES `avaliacaoDigital`.`questao` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `avaliacaoDigital`.`avaliacaoTurmaAluno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `avaliacaoDigital`.`avaliacaoTurmaAluno` (
  `avaliacaoCodigo` INT UNSIGNED NOT NULL,
  `turmaCodigo` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`avaliacaoCodigo`, `turmaCodigo`),
  INDEX `fkTurmaCodigo_idx` (`turmaCodigo` ASC),
  CONSTRAINT `fkATAAvaliacaoCodigo`
    FOREIGN KEY (`avaliacaoCodigo`)
    REFERENCES `avaliacaoDigital`.`avaliacao` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fkATATurmaCodigo`
    FOREIGN KEY (`turmaCodigo`)
    REFERENCES `avaliacaoDigital`.`turmaAluno` (`turmaCodigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
