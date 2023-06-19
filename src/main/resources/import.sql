-- Preenchendo a tabela usuario
INSERT INTO usuario (login, senha) 
VALUES ('admin', 'Dqea/mtuitkfQ0CNCFp54QoW6mZLhWqZ1CbtUcqWegJ0YmJOEDWZjpiqBz31LlpSJ/Ro4Yz5wcVsG7UjDij74g==');

-- Preenchendo a tabela perfis
INSERT INTO perfis (id_usuario, perfil)
VALUES (1, 'Admin');

-- Preenchendo a tabela usuario
INSERT INTO usuario (login, senha) 
VALUES ('user', 'rfn8b4ovxCwc0nvvqCrFPBFIApxDRppESGK0krCm0dzvrVBJ7xHLaLCQQmq9L5QSUemLAtO7dwrnhNgq8AVu3A==');

-- Preenchendo a tabela perfis
INSERT INTO perfis (id_usuario, perfil)
VALUES (2, 'User');

-- Preenchendo a tabela estado
INSERT INTO estado (nome, sigla)
VALUES ('São Paulo', 'SP');

-- Preenchendo a tabela municipio
INSERT INTO municipio (nome, id_estado)
VALUES ('São Paulo', 1);

-- Preenchendo a tabela telefone
INSERT INTO telefone (ddd, numero) 
VALUES ('99', '9 9108-1780');

-- Preenchendo a tabela endereco
INSERT INTO endereco (principal, logradouro, numero, complemento, bairro, cep, id_municipio)
VALUES (true, 'Rua das Flores', '123', 'Apartamento 1A', 'Centro', '01234-567', 1);

-- Preenchendo a tabela usuario
INSERT INTO usuario (nome, email, cpf, sexo, id_telefone, id_endereco) 
VALUES ('João Silva', 'joao.silva@example.com', '12345678900', 'MASCULINO', 1, 1);

-- Preenchendo a tabela estado
INSERT INTO estado (nome, sigla)
VALUES ('Rio de Janeiro', 'RJ');

-- Preenchendo a tabela municipio
INSERT INTO municipio (nome, id_estado)
VALUES ('Rio de Janeiro', 2);

-- Preenchendo a tabela telefone
INSERT INTO telefone (ddd, numero)
VALUES ('21', '9 8765-4321');

-- Preenchendo a tabela endereco
INSERT INTO endereco (principal, logradouro, numero, complemento, bairro, cep, id_municipio)
VALUES (false, 'Avenida Copacabana', '456', 'Apartamento 2B', 'Copacabana', '98765-432', 2);

-- Preenchendo a tabela produto
INSERT INTO produto (nome, descricao, preco, estoque)
VALUES ('Placa de Video', '8GB GDDR6', 3499.90, 100);

-- Preenchendo a tabela telefone
INSERT INTO telefone (ddd, numero)
VALUES ('11', '9999-8888');

-- Preenchendo a tabela fabricante
INSERT INTO fabricante (nome, website)
VALUES ('NVidia', 'nvidia.com');

-- Preenchendo a tabela hardware
/* INSERT INTO hardware (modelo, lancamento, nivel, integridade, id_fabricante)
VALUES ('GeForce RTX 3080', '2020-09-17', 'HIGH_END', 'NOVO', 1); */