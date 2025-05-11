INSERT INTO TB_USUARIOS (id, email, nome, senha, permissao_id)
VALUES (101, 'admin02@gmail.com', 'admin', '$2a$12$joBkW0je10Mvz0n0aKERi.iH7MYObnyR0hNK4AU85UR2oCHdV.iia', 1);
INSERT INTO TB_USUARIOS (id, email, nome, senha, permissao_id)
VALUES (102, 'api.usuario.dev@gmail.com', 'Usuario Dev', '$2a$12$joBkW0je10Mvz0n0aKERi.iH7MYObnyR0hNK4AU85UR2oCHdV.iia', 2);
INSERT INTO TB_USUARIOS (id, email, nome, senha, permissao_id)
VALUES (103, 'api.usuario@gmail.com', 'Usuario', '$2a$12$joBkW0je10Mvz0n0aKERi.iH7MYObnyR0hNK4AU85UR2oCHdV.iia', 2);

INSERT INTO TB_LIVROS (id, titulo, autor, genero, isbn, disponivel)
VALUES (201, 'O Pequeno Principe', 'Antonie de Saint-Exupery', 'AVENTURA', '9783140464079', true);
INSERT INTO TB_LIVROS (id, titulo, autor, genero, isbn, disponivel)
VALUES (202, 'O Pequeno Principe 2', 'Antonie de Saint-Exupery', 'AVENTURA', '9783958625402', true);

INSERT INTO TB_EMPRESTIMOS(usuario_id, livro_id, devolvido, id, prazo_inicial, prazo_final)
VALUES (102, 202, false, 301, '2025-04-10 14:30:00', '2025-04-13 14:30:00');

UPDATE TB_LIVROS
SET disponivel = false
WHERE id = 202;
INSERT INTO TB_USUARIOS_EMPRESTIMOS (usuario_id, emprestimos_id)
VALUES (102, 301);