-- Script de inicialização do banco de dados Liter Alura
-- Banco H2 (em memória) - não requer instalação externa

-- Comentário: As tabelas serão criadas automaticamente pelo Hibernate
-- com a configuração spring.jpa.hibernate.ddl-auto=create-drop

-- Para acessar o console H2: http://localhost:8080/h2-console
-- JDBC URL: jdbc:h2:mem:liter_alura
-- Username: sa
-- Password: (deixe em branco)

-- Exemplo de consultas úteis para teste:

-- Contar total de livros
-- SELECT COUNT(*) FROM livros;

-- Contar total de autores
-- SELECT COUNT(*) FROM autores;

-- Listar livros com seus autores
-- SELECT l.titulo, a.nome 
-- FROM livros l 
-- LEFT JOIN autores a ON l.id = a.livro_id;

-- Contar livros por idioma
-- SELECT i.idioma, COUNT(*) as quantidade
-- FROM livros l 
-- JOIN livro_idiomas i ON l.id = i.livro_id 
-- GROUP BY i.idioma;

-- Listar autores vivos em um ano específico
-- SELECT nome, ano_nascimento, ano_falecimento
-- FROM autores 
-- WHERE ano_nascimento <= 1900 
-- AND (ano_falecimento IS NULL OR ano_falecimento >= 1900);

-- Estatísticas gerais
-- SELECT 
--     (SELECT COUNT(*) FROM livros) as total_livros,
--     (SELECT COUNT(*) FROM autores) as total_autores,
--     (SELECT COUNT(DISTINCT idioma) FROM livro_idiomas) as idiomas_diferentes; 