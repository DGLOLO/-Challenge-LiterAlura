package Danilo.Challenge_LiterAlura.service;

import Danilo.Challenge_LiterAlura.dto.LivroDTO;
import Danilo.Challenge_LiterAlura.dto.AutorDTO;
import Danilo.Challenge_LiterAlura.model.Livro;
import Danilo.Challenge_LiterAlura.model.Autor;
import Danilo.Challenge_LiterAlura.repository.LivroRepository;
import Danilo.Challenge_LiterAlura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private AutorRepository autorRepository;
    
    /**
     * Converte um LivroDTO para entidade Livro e persiste no banco
     * Se o autor não existir, cria um novo
     */
    @Transactional
    public Livro salvarLivro(LivroDTO livroDTO) {
        // Verificar se o livro já existe pela API ID
        if (livroRepository.existsByApiId(livroDTO.id())) {
            return livroRepository.findByApiId(livroDTO.id()).orElse(null);
        }
        
        // Buscar ou criar o autor
        Autor autor = buscarOuCriarAutor(livroDTO.autores().get(0));
        
        // Criar o livro
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.titulo());
        livro.setAutor(autor);
        livro.setIdiomas(livroDTO.idiomas());
        livro.setDownloads(livroDTO.downloads());
        livro.setApiId(livroDTO.id());
        
        // Salvar o livro (o autor será salvo em cascata)
        return livroRepository.save(livro);
    }
    
    /**
     * Busca um autor existente ou cria um novo
     */
    private Autor buscarOuCriarAutor(AutorDTO autorDTO) {
        // Tentar encontrar autor existente
        Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(autorDTO.nome());
        
        if (autorExistente.isPresent()) {
            return autorExistente.get();
        }
        
        // Criar novo autor
        Autor novoAutor = new Autor();
        novoAutor.setNome(autorDTO.nome());
        novoAutor.setAnoNascimento(autorDTO.anoNascimento());
        novoAutor.setAnoFalecimento(autorDTO.anoFalecimento());
        
        return autorRepository.save(novoAutor);
    }
    
    /**
     * Salva múltiplos livros da API
     */
    @Transactional
    public List<Livro> salvarLivros(List<LivroDTO> livrosDTO) {
        return livrosDTO.stream()
                .map(this::salvarLivro)
                .filter(livro -> livro != null)
                .toList();
    }
    
    /**
     * Busca livros por título no banco local
     */
    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    /**
     * Busca livros por autor no banco local
     */
    public List<Livro> buscarLivrosPorAutor(String nomeAutor) {
        return livroRepository.findByAutorNomeContainingIgnoreCase(nomeAutor);
    }
    
    /**
     * Busca livros por idioma no banco local
     */
    public List<Livro> buscarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }
    
    /**
     * Lista todos os livros ordenados por downloads
     */
    public List<Livro> listarLivrosPopulares() {
        return livroRepository.findAllByOrderByDownloadsDesc();
    }
    
    /**
     * Busca autores vivos em um determinado ano
     */
    public List<Autor> buscarAutoresVivosNoAno(int ano) {
        return autorRepository.findAutoresVivosNoAno(ano);
    }
    
    /**
     * Conta total de livros no banco
     */
    public long contarLivros() {
        return livroRepository.count();
    }
    
    /**
     * Conta total de autores no banco
     */
    public long contarAutores() {
        return autorRepository.count();
    }
}

