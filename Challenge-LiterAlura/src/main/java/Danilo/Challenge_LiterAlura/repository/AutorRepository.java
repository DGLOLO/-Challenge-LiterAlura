package Danilo.Challenge_LiterAlura.repository;

import Danilo.Challenge_LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    // Buscar autor por nome (ignorando maiúsculas/minúsculas)
    Optional<Autor> findByNomeIgnoreCase(String nome);
    
    // Buscar autores que contêm o nome especificado
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar autores vivos em um determinado ano
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivosNoAno(@Param("ano") int ano);
    
    // Buscar autores nascidos em um determinado ano
    List<Autor> findByAnoNascimento(Integer anoNascimento);
    
    // Buscar autores que faleceram em um determinado ano
    List<Autor> findByAnoFalecimento(Integer anoFalecimento);
    
    // Buscar autores por período de vida
    @Query("SELECT a FROM Autor a WHERE a.anoNascimento BETWEEN :anoInicio AND :anoFim")
    List<Autor> findAutoresPorPeriodo(@Param("anoInicio") int anoInicio, @Param("anoFim") int anoFim);
    
    // Verificar se um autor já existe (para evitar duplicatas)
    boolean existsByNomeIgnoreCase(String nome);
}

