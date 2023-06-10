-- Preenchendo a tabela usuario
INSERT INTO usuario (login, senha) 
VALUES ('joao', 'rokCo68LhEWNtADgaDdExxmgAYfhYIPZ4sybRUswi3SDaMIXU1N7DyaRKum8m3LKpkKEHRHLSULaQKCg/rcgeQ==');

-- Preenchendo a tabela perfis
INSERT INTO perfis (id_usuario, perfil)
VALUES (1, 'Admin');

-- Preenchendo a tabela estado
INSERT INTO estado (nome, sigla)
VALUES ('São Paulo', 'SP');

-- Preenchendo a tabela municipio
INSERT INTO municipio (nome, id_estado)
VALUES ('São Paulo', 1);

-- Preenchendo a tabela endereco
INSERT INTO endereco (principal, logradouro, numero, complemento, bairro, cep, id_municipio)
VALUES (true, 'Avenida Paulista', '1000', 'Sala 200', 'Bela Vista', '01310-100', 1);

-- Preenchendo a tabela telefone
INSERT INTO telefone (ddd, numero)
VALUES ('11', '9999-8888');

/* -- Preenchendo a tabela fabricante
INSERT INTO fabricante (nome, website)
VALUES ('NVidia', 'nvidia.com');

-- Preenchendo a tabela hardware
INSERT INTO hardware (modelo, lancamento, nivel, integridade, id_fabricante)
VALUES ('GeForce RTX 3080', '2020-09-17', 'HIGH_END', 'NOVO', 1); */