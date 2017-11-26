use avaliacaoDigital;

#PROFESSOR

insert into professor(nome) values("Carlos Pivotto");
insert into professor(nome) values("Tomás de Aquino Tinoco Botelho");
insert into professor(nome) values("Diego London");
insert into professor(nome) values("Jonh Carvalho");
select * from professor;

#MODULO

insert into modulo(nome) values("PDJA6aNSab_RS - Projeto de Bloco BEJA6 - TCC I");
insert into modulo(nome) values("PDJA6aNSab_RS - Aplicações Web com Tecnologia Java");
insert into modulo(nome) values("PDJA6aNSab_RS - Serviços com Tecnologia Java");
insert into modulo(nome) values("PDJA6aNSab_RS - Projeto de Bloco BEJA6 - TCC II");

insert into modulo(nome) values("PDJA7aNSab_RS - Projeto de Bloco BEJA7 - TCC I");
insert into modulo(nome) values("PDJA7aNSab_RS - Aplicações Web com Tecnologia .NET");
insert into modulo(nome) values("PDJA7aNSab_RS - Serviços com Tecnologia .NET");
insert into modulo(nome) values("PDJA7aNSab_RS - Projeto de Bloco BEJA7 - TCC II");

insert into modulo(nome) values("PDJA8aNSab_RS - Projeto de Bloco BEJA8 - TCC I");
insert into modulo(nome) values("PDJA8aNSab_RS - Aplicações Web com Mobile");
insert into modulo(nome) values("PDJA8aNSab_RS - Serviços com Mobile");
insert into modulo(nome) values("PDJA8aNSab_RS - Projeto de Bloco BEJA8 - TCC II");

insert into modulo(nome) values("PDJA3aNSab_RJ - Projeto de Bloco BEJA3 - TCC I");
insert into modulo(nome) values("PDJA3aNSab_RJ - Aplicações Web com Tecnologia Java");
insert into modulo(nome) values("PDJA3aNSab_RJ - Serviços com Tecnologia Java");
insert into modulo(nome) values("PDJA3aNSab_RJ - Projeto de Bloco BEJA3 - TCC II");

insert into modulo(nome) values("PDJA4aNSab_RJ - Projeto de Bloco BEJA4 - TCC I");
insert into modulo(nome) values("PDJA4aNSab_RJ - Aplicações Web com Tecnologia .NET");
insert into modulo(nome) values("PDJA4aNSab_RJ - Serviços com Tecnologia .NET");
insert into modulo(nome) values("PDJA4aNSab_RJ - Projeto de Bloco BEJA4 - TCC II");

insert into modulo(nome) values("PDJA5aNSab_RJ - Projeto de Bloco BEJA5 - TCC I");
insert into modulo(nome) values("PDJA5aNSab_RJ - Aplicações Web com Mobile");
insert into modulo(nome) values("PDJA5aNSab_RJ - Serviços com Mobile");
insert into modulo(nome) values("PDJA5aNSab_RJ - Projeto de Bloco BEJA5 - TCC II");

select * from modulo;

#USUARIO

insert into usuario(nome, senha, acesso, login, administrador)  
values("jose de tal", "1234", "2017/11/22", "josedetal", 0);

insert into usuario(nome, senha, acesso, login, administrador)  
values("joao de tal", "43214", "2017/11/30", "joaodetal", 0);

insert into usuario(nome, senha, acesso, login, administrador)  
values("paulo andre medeiros", "32313", "2017/11/22", "pam8039", 0);

insert into usuario(nome, senha, acesso, login, administrador)  
values("cristina soares dos santos", "1232", "2017/11/22", "css9302", 0);

insert into usuario(nome, senha, acesso, login, administrador)  
values("maria regina silva", "4321", "2017/11/22", "mariasilva", 0);

insert into usuario(nome, senha, acesso, login, administrador)  
values("clarice pereira", "r3g6", "2017/11/22", "claricep", 0);

select * from usuario;

#ALUNO

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(07043423912, "Jose de tal", "jose.de.tal@gmail.com", 123, "Maria de tal", "1990/10/05", 4543230631, 'Masculino', 1);

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(96543280912, "Joao de tal", "joao.de.tal@terra.com", 124, "Claudia de tal", "1989/11/05", 96643280912, 'Masculino', 2);

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(86533280912, "paulo andre medeiros", "paulo.medeiros93@gmail.com", 125, "Renata Medeiros", "1984/03/07", 86443280912, 'Masculino', 3);

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(54534480912, "cristina soares dos santos", "cristina.santos@gmail.com", 126, "Maria antonieta santos", "1973/08/05", 75443234912, 'Feminino', 4);

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(32236798037, "maria regina silva", "msilva87@gmail.com", 127, "Tais silva", "1979/04/27", 2549739356, 'Feminino', 5);

insert into aluno(cpf, nome, email, matricula, nomeMae, dtNascimento, carteiraIdentidade, sexo, usuarioCodigo) 
values(57409848244, "clarice pereira", "cp9803.casa@yahoo.com", 128, "Carla pereira", "1985/11/23", 9458739704, 'Feminino', 6);

select * from aluno;

#TURMA

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RS- Java", 1, 1);

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RS - .NET", 2, 7);

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RS - Mobile", 3, 10);

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RJ - .NET", 4, 7);

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RJ - Java", 3, 3);

insert into turma(inicio, fim, descricao, professorCodigo, moduloCodigo)
values("2017/07/22 08:30:00", "2017/12/16 17:30:00", "Turma RJ - Mobile", 2, 11);

select * from turma;

update turma set fim = "2017/12/16 17:30:00" where codigo >= 1 and codigo <= 6;

#TURMAALUNO

insert into turmaAluno(alunoCodigo, turmaCodigo) values (1,1);
insert into turmaAluno(alunoCodigo, turmaCodigo) values (2,2);
insert into turmaAluno(alunoCodigo, turmaCodigo) values (3,3);
insert into turmaAluno(alunoCodigo, turmaCodigo) values (4,4);
insert into turmaAluno(alunoCodigo, turmaCodigo) values (5,5);
insert into turmaAluno(alunoCodigo, turmaCodigo) values (6,6);
select * from turmaAluno;

