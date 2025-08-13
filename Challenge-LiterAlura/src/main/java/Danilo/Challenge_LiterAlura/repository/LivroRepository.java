package Danilo.Challenge_LiterAlura.repository;

import Danilo.Challenge_LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    // Buscar livro por título (ignorando maiúsculas/minúsculas)
    Optional<Livro> findByTituloIgnoreCase(String titulo);
    
    // Buscar livros que contêm o título especificado
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar livros por autor
    List<Livro> findByAutorNomeContainingIgnoreCase(String nomeAutor);
    
    // Buscar livros por idioma específico
    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    List<Livro> findByIdioma(@Param("idioma") String idioma);
    
    // Buscar livros por autor ID
    List<Livro> findByAutorId(Long autorId);
    
    // Buscar livros ordenados por número de downloads (decrescente)
    List<Livro> findAllByOrderByDownloadsDesc();
    
    // Buscar top N livros mais baixados
    List<Livro> findTop10ByOrderByDownloadsDesc();
    
    // Buscar livros por API ID (para evitar duplicatas)
    Optional<Livro> findByApiId(Long apiId);
    
    // Verificar se um livro já existe pela API ID
    boolean existsByApiId(Long apiId);
    
    // Buscar livros com downloads acima de um valor
    List<Livro> findByDownloadsGreaterThan(Integer downloads);
    
    // Contar livros por idioma
    @Query("SELECT i, COUNT(l) FROM Livro l JOIN l.idiomas i GROUP BY i ORDER BY COUNT(l) DESC")
    List<Object[]> countLivrosPorIdioma();
    
    // Buscar livros por período de downloads
    @Query("SELECT l FROM Livro l WHERE l.downloads BETWEEN :minDownloads AND :maxDownloads")
    List<Livro> findByDownloadsBetween(@Param("minDownloads") Integer minDownloads, @Param("maxDownloads") Integer maxDownloads);
}

