-- INSERT INTO fabricante (id, nome, website)
-- VALUES (1, 'Gigabyte', 'amd.com');

-- INSERT INTO telefone (id, ddd, numero)
-- VALUES (1, '63', '9 1234-5678');

-- INSERT INTO estado (id, nome, sigla)
-- VALUES (1, 'São Paulo', 'SP');

-- INSERT INTO municipio (id, nome, estado_id)
-- VALUES (1, 'São Paulo', 1);

-- INSERT INTO endereco (id, principal, logradouro, numero, complemento, bairro, cep, municipio_id)
-- VALUES (1, true, 'Rua A', '123', 'Apto 101', 'Centro', '01234567', 1);

-- INSERT INTO produto (id, nome, descricao, preco, estoque)
-- VALUES (1, 'Processador', '8 Cores 16 Threads', 1499.90, 100);

-- INSERT INTO cliente (id, nome, email, cpf)
-- VALUES (1, 'João Silva', 'joao.silva@gmail.com', '12345678901');

-- INSERT INTO public.cliente_endereco(
-- 	cliente_id, endereco_id)
-- 	VALUES (1, 1);

-- INSERT INTO public.cliente_telefone(
--     cliente_id, telefone_id)
--     VALUES (1, 1);

-- INSERT INTO public.cliente_listadesejo(
-- 	cliente_id, listadesejo_id)
-- 	VALUES (1, 1);