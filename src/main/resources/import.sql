INSERT INTO pessoa (nome) 
VALUES ('Joao');

INSERT INTO pessoafisica (id, cpf, sexo) 
VALUES (1, '111.111.111-11', 1);

INSERT INTO usuario (login, senha, id_pessoa_fisica) 
VALUES ('joao', 'rokCo68LhEWNtADgaDdExxmgAYfhYIPZ4sybRUswi3SDaMIXU1N7DyaRKum8m3LKpkKEHRHLSULaQKCg/rcgeQ==', 1);

INSERT INTO perfis (id_usuario, perfil) 
VALUES (1, 'Admin');