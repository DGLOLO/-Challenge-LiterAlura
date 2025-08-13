package Danilo.Challenge_LiterAlura;

import Danilo.Challenge_LiterAlura.dto.RespostaApiDTO;
import Danilo.Challenge_LiterAlura.dto.LivroDTO;
import Danilo.Challenge_LiterAlura.model.Livro;
import Danilo.Challenge_LiterAlura.model.Autor;
import Danilo.Challenge_LiterAlura.service.ConsumoApi;
import Danilo.Challenge_LiterAlura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.List;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LivroService livroService;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("🚀 === CHALLENGE LITER ALURA ===\n");
		
		// Testar se a API está funcionando primeiro
		if (!testarApi()) {
			System.out.println("❌ API não está funcionando. Verifique sua conexão.");
			return;
		}
		
		// Iniciar menu interativo
		exibirMenu();
	}
	
	private boolean testarApi() {
		try {
			var consumoApi = new ConsumoApi();
			String jsonBruto = consumoApi.obterDados("https://gutendex.com/books");
			return !jsonBruto.isEmpty() && jsonBruto.contains("count");
		} catch (Exception e) {
			return false;
		}
	}
	
	private void exibirMenu() {
		Scanner scanner = new Scanner(System.in);
		boolean continuar = true;
		
		while (continuar) {
			System.out.println("\n" + "═".repeat(50));
			System.out.println("📚 MENU PRINCIPAL - LITER ALURA");
			System.out.println("═".repeat(50));
			System.out.println("1️⃣  - Buscar livros por título (API)");
			System.out.println("2️⃣  - Listar livros mais populares (API)");
			System.out.println("3️⃣  - Buscar livros por idioma (API)");
			System.out.println("4️⃣  - Buscar livros por autor (API)");
			System.out.println("5️⃣  - Mostrar estatísticas da API");
			System.out.println("6️⃣  - Salvar livros da API no banco local");
			System.out.println("7️⃣  - Estatísticas do banco local");
			System.out.println("8️⃣  - Buscar livros no banco local");
			System.out.println("9️⃣  - Buscar autores vivos em determinado ano");
			System.out.println("0️⃣  - Sair");
			System.out.println("═".repeat(50));
			System.out.print("Escolha uma opção: ");
			
			try {
				int opcao = Integer.parseInt(scanner.nextLine().trim());
				
				switch (opcao) {
					case 1:
						buscarLivrosPorTitulo(scanner);
						break;
					case 2:
						listarLivrosPopulares(scanner);
						break;
					case 3:
						buscarLivrosPorIdioma(scanner);
						break;
					case 4:
						buscarLivrosPorAutor(scanner);
						break;
					case 5:
						mostrarEstatisticas();
						break;
					case 6:
						salvarLivrosNoBanco(scanner);
						break;
					case 7:
						mostrarEstatisticasBancoLocal();
						break;
					case 8:
						buscarLivrosBancoLocal(scanner);
						break;
					case 9:
						buscarAutoresVivos(scanner);
						break;
					case 0:
						System.out.println("👋 Obrigado por usar o Liter Alura!");
						continuar = false;
						break;
					default:
						System.out.println("❌ Opção inválida! Escolha um número de 0 a 9.");
				}
			} catch (NumberFormatException e) {
				System.out.println("❌ Erro: Digite apenas números!");
			} catch (Exception e) {
				System.out.println("❌ Erro inesperado: " + e.getMessage());
			}
		}
		
		scanner.close();
	}
	
	private void buscarLivrosPorTitulo(Scanner scanner) {
		System.out.println("\n🔍 === BUSCAR LIVROS POR TÍTULO ===");
		System.out.print("Digite o título ou parte do título: ");
		String titulo = scanner.nextLine().trim();
		
		if (titulo.isEmpty()) {
			System.out.println("❌ Título não pode estar vazio!");
			return;
		}
		
		try {
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			var livrosEncontrados = resposta.livros().stream()
				.filter(livro -> livro.titulo().toLowerCase().contains(titulo.toLowerCase()))
				.toList();
			
			if (livrosEncontrados.isEmpty()) {
				System.out.println("📚 Nenhum livro encontrado com o título: " + titulo);
			} else {
				System.out.println("📚 Livros encontrados (" + livrosEncontrados.size() + "):");
				livrosEncontrados.forEach(livro -> {
					System.out.println("─".repeat(40));
					System.out.println("📖 ID: " + livro.id());
					System.out.println("📚 Título: " + livro.titulo());
					if (!livro.autores().isEmpty()) {
						System.out.println("👤 Autor: " + livro.autores().get(0).nome());
					}
					System.out.println("🌍 Idiomas: " + String.join(", ", livro.idiomas()));
					System.out.println("⬇️  Downloads: " + livro.downloads());
				});
			}
		} catch (Exception e) {
			System.out.println("❌ Erro ao buscar livros: " + e.getMessage());
		}
	}
	
	private void listarLivrosPopulares(Scanner scanner) {
		System.out.println("\n🔥 === LIVROS MAIS POPULARES ===");
		System.out.print("Quantos livros deseja ver? (1-20): ");
		
		try {
			int quantidade = Integer.parseInt(scanner.nextLine().trim());
			if (quantidade < 1 || quantidade > 20) {
				System.out.println("❌ Quantidade deve ser entre 1 e 20!");
				return;
			}
			
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			var livrosOrdenados = resposta.livros().stream()
				.sorted((l1, l2) -> Integer.compare(l2.downloads(), l1.downloads()))
				.limit(quantidade)
				.toList();
			
			System.out.println("🔥 Top " + quantidade + " livros mais baixados:");
			livrosOrdenados.forEach(livro -> {
				System.out.println("─".repeat(40));
				System.out.println("📚 " + livro.titulo());
				System.out.println("👤 Autor: " + (livro.autores().isEmpty() ? "N/A" : livro.autores().get(0).nome()));
				System.out.println("⬇️  Downloads: " + livro.downloads());
			});
			
		} catch (NumberFormatException e) {
			System.out.println("❌ Digite um número válido!");
		} catch (Exception e) {
			System.out.println("❌ Erro ao listar livros: " + e.getMessage());
		}
	}
	
	private void buscarLivrosPorIdioma(Scanner scanner) {
		System.out.println("\n🌍 === BUSCAR LIVROS POR IDIOMA ===");
		System.out.println("Idiomas disponíveis: en (inglês), es (espanhol), fr (francês), pt (português)");
		System.out.print("Digite o código do idioma: ");
		String idioma = scanner.nextLine().trim().toLowerCase();
		
		if (idioma.isEmpty()) {
			System.out.println("❌ Idioma não pode estar vazio!");
			return;
		}
		
		try {
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			var livrosPorIdioma = resposta.livros().stream()
				.filter(livro -> livro.idiomas().contains(idioma))
				.toList();
			
			if (livrosPorIdioma.isEmpty()) {
				System.out.println("📚 Nenhum livro encontrado no idioma: " + idioma);
			} else {
				System.out.println("📚 Livros em " + idioma + " (" + livrosPorIdioma.size() + "):");
				livrosPorIdioma.forEach(livro -> {
					System.out.println("─".repeat(40));
					System.out.println("📖 " + livro.titulo());
					if (!livro.autores().isEmpty()) {
						System.out.println("👤 " + livro.autores().get(0).nome());
					}
				});
			}
		} catch (Exception e) {
			System.out.println("❌ Erro ao buscar por idioma: " + e.getMessage());
		}
	}
	
	private void buscarLivrosPorAutor(Scanner scanner) {
		System.out.println("\n👤 === BUSCAR LIVROS POR AUTOR ===");
		System.out.print("Digite o nome do autor: ");
		String nomeAutor = scanner.nextLine().trim();
		
		if (nomeAutor.isEmpty()) {
			System.out.println("❌ Nome do autor não pode estar vazio!");
			return;
		}
		
		try {
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			var livrosPorAutor = resposta.livros().stream()
				.filter(livro -> livro.autores().stream()
					.anyMatch(autor -> autor.nome().toLowerCase().contains(nomeAutor.toLowerCase())))
				.toList();
			
			if (livrosPorAutor.isEmpty()) {
				System.out.println("📚 Nenhum livro encontrado do autor: " + nomeAutor);
			} else {
				System.out.println("📚 Livros do autor " + nomeAutor + " (" + livrosPorAutor.size() + "):");
				livrosPorAutor.forEach(livro -> {
					System.out.println("─".repeat(40));
					System.out.println("📖 " + livro.titulo());
					System.out.println("⬇️  Downloads: " + livro.downloads());
				});
			}
		} catch (Exception e) {
			System.out.println("❌ Erro ao buscar por autor: " + e.getMessage());
		}
	}
	
	private void mostrarEstatisticas() {
		System.out.println("\n📊 === ESTATÍSTICAS DA API ===");
		
		try {
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			System.out.println("📚 Total de livros na API: " + resposta.total());
			System.out.println("📖 Livros nesta página: " + resposta.livros().size());
			
			// Estatísticas dos livros disponíveis
			long totalDownloads = resposta.livros().stream()
				.mapToLong(livro -> livro.downloads() != null ? livro.downloads() : 0)
				.sum();
			
			double mediaDownloads = resposta.livros().stream()
				.filter(livro -> livro.downloads() != null)
				.mapToDouble(livro -> livro.downloads())
				.average()
				.orElse(0.0);
			
			System.out.println("⬇️  Total de downloads: " + totalDownloads);
			System.out.println("📈 Média de downloads: " + String.format("%.0f", mediaDownloads));
			
			// Idiomas mais comuns
			var idiomasCount = resposta.livros().stream()
				.flatMap(livro -> livro.idiomas().stream())
				.collect(java.util.stream.Collectors.groupingBy(
					idioma -> idioma,
					java.util.stream.Collectors.counting()
				));
			
			System.out.println("\n🌍 Distribuição por idioma:");
			idiomasCount.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
				.forEach(entry -> System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " livros"));
			
		} catch (Exception e) {
			System.out.println("❌ Erro ao mostrar estatísticas: " + e.getMessage());
		}
	}
	
	private void salvarLivrosNoBanco(Scanner scanner) {
		System.out.println("\n💾 === SALVAR LIVROS NO BANCO LOCAL ===");
		System.out.print("Quantos livros deseja salvar? (1-20): ");
		
		try {
			int quantidade = Integer.parseInt(scanner.nextLine().trim());
			if (quantidade < 1 || quantidade > 20) {
				System.out.println("❌ Quantidade deve ser entre 1 e 20!");
				return;
			}
			
			var consumoApi = new ConsumoApi();
			RespostaApiDTO resposta = consumoApi.obterDadosMapeados("https://gutendex.com/books");
			
			var livrosParaSalvar = resposta.livros().stream()
				.limit(quantidade)
				.toList();
			
			System.out.println("⏳ Salvando " + livrosParaSalvar.size() + " livros no banco...");
			var livrosSalvos = livroService.salvarLivros(livrosParaSalvar);
			
			System.out.println("✅ " + livrosSalvos.size() + " livros salvos com sucesso!");
			System.out.println("📊 Total no banco: " + livroService.contarLivros() + " livros, " + livroService.contarAutores() + " autores");
			
		} catch (NumberFormatException e) {
			System.out.println("❌ Digite um número válido!");
		} catch (Exception e) {
			System.out.println("❌ Erro ao salvar livros: " + e.getMessage());
		}
	}
	
	private void mostrarEstatisticasBancoLocal() {
		System.out.println("\n📊 === ESTATÍSTICAS DO BANCO LOCAL ===");
		
		try {
			long totalLivros = livroService.contarLivros();
			long totalAutores = livroService.contarAutores();
			
			System.out.println("📚 Total de livros no banco: " + totalLivros);
			System.out.println("👥 Total de autores no banco: " + totalAutores);
			
			if (totalLivros > 0) {
				// Estatísticas por idioma
				System.out.println("\n🌍 Distribuição por idioma:");
				var livrosPopulares = livroService.listarLivrosPopulares();
				
				// Contar livros por idioma usando Streams
				var idiomasCount = livrosPopulares.stream()
					.flatMap(livro -> livro.getIdiomas().stream())
					.collect(java.util.stream.Collectors.groupingBy(
						idioma -> idioma,
						java.util.stream.Collectors.counting()
					));
				
				idiomasCount.entrySet().stream()
					.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
					.forEach(entry -> System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " livros"));
				
				// Top 5 livros mais baixados
				System.out.println("\n🔥 Top 5 livros mais baixados:");
				livrosPopulares.stream()
					.limit(5)
					.forEach(livro -> {
						System.out.println("─".repeat(40));
						System.out.println("📖 " + livro.getTitulo());
						System.out.println("👤 " + livro.getAutor().getNome());
						System.out.println("⬇️  " + livro.getDownloads() + " downloads");
					});
			} else {
				System.out.println("📝 Nenhum livro encontrado no banco. Use a opção 6 para salvar livros da API.");
			}
			
		} catch (Exception e) {
			System.out.println("❌ Erro ao mostrar estatísticas: " + e.getMessage());
		}
	}
	
	private void buscarLivrosBancoLocal(Scanner scanner) {
		System.out.println("\n🔍 === BUSCAR LIVROS NO BANCO LOCAL ===");
		System.out.println("1 - Por título");
		System.out.println("2 - Por autor");
		System.out.println("3 - Por idioma");
		System.out.print("Escolha o tipo de busca: ");
		
		try {
			int tipo = Integer.parseInt(scanner.nextLine().trim());
			
			switch (tipo) {
				case 1:
					System.out.print("Digite o título: ");
					String titulo = scanner.nextLine().trim();
					var livrosTitulo = livroService.buscarLivrosPorTitulo(titulo);
					exibirLivrosEncontrados(livrosTitulo, "título: " + titulo);
					break;
				case 2:
					System.out.print("Digite o nome do autor: ");
					String autor = scanner.nextLine().trim();
					var livrosAutor = livroService.buscarLivrosPorAutor(autor);
					exibirLivrosEncontrados(livrosAutor, "autor: " + autor);
					break;
				case 3:
					System.out.print("Digite o idioma (en, es, fr, pt): ");
					String idioma = scanner.nextLine().trim();
					var livrosIdioma = livroService.buscarLivrosPorIdioma(idioma);
					exibirLivrosEncontrados(livrosIdioma, "idioma: " + idioma);
					break;
				default:
					System.out.println("❌ Opção inválida!");
			}
		} catch (NumberFormatException e) {
			System.out.println("❌ Digite um número válido!");
		} catch (Exception e) {
			System.out.println("❌ Erro na busca: " + e.getMessage());
		}
	}
	
	private void exibirLivrosEncontrados(List<Livro> livros, String criterio) {
		if (livros.isEmpty()) {
			System.out.println("📚 Nenhum livro encontrado com " + criterio);
		} else {
			System.out.println("📚 Livros encontrados (" + livros.size() + ") com " + criterio + ":");
			livros.forEach(livro -> {
				System.out.println("─".repeat(40));
				System.out.println("📖 " + livro.getTitulo());
				System.out.println("👤 " + livro.getAutor().getNome());
				System.out.println("🌍 " + String.join(", ", livro.getIdiomas()));
				System.out.println("⬇️  " + livro.getDownloads() + " downloads");
			});
		}
	}
	
	private void buscarAutoresVivos(Scanner scanner) {
		System.out.println("\n👤 === BUSCAR AUTORES VIVOS EM DETERMINADO ANO ===");
		System.out.print("Digite o ano: ");
		
		try {
			int ano = Integer.parseInt(scanner.nextLine().trim());
			if (ano < 1000 || ano > 2100) {
				System.out.println("❌ Ano deve estar entre 1000 e 2100!");
				return;
			}
			
			var autoresVivos = livroService.buscarAutoresVivosNoAno(ano);
			
			if (autoresVivos.isEmpty()) {
				System.out.println("👤 Nenhum autor encontrado vivo no ano " + ano);
			} else {
				System.out.println("👤 Autores vivos no ano " + ano + " (" + autoresVivos.size() + "):");
				autoresVivos.forEach(autor -> {
					System.out.println("─".repeat(40));
					System.out.println("👤 " + autor.getNome());
					System.out.println("📅 Nascimento: " + autor.getAnoNascimento());
					System.out.println("📅 Falecimento: " + (autor.getAnoFalecimento() != null ? autor.getAnoFalecimento() : "Ainda vivo"));
				});
			}
			
		} catch (NumberFormatException e) {
			System.out.println("❌ Digite um ano válido!");
		} catch (Exception e) {
			System.out.println("❌ Erro ao buscar autores: " + e.getMessage());
		}
	}
}



