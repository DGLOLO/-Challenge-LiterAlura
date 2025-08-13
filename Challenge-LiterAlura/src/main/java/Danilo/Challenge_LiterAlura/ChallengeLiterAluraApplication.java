package Danilo.Challenge_LiterAlura;

import Danilo.Challenge_LiterAlura.model.RespostaApi;
import Danilo.Challenge_LiterAlura.service.ConsumoApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("🚀 === INICIANDO CHALLENGE LITER ALURA ===\n");
		
		var consumoApi = new ConsumoApi();
		String url = "https://gutendex.com/books";
		
		System.out.println("📍 URL da API: " + url);
		System.out.println("⏳ Fazendo requisição HTTP...\n");
		
		try {
			// 1. Obter dados brutos da API
			System.out.println("📡 ETAPA 1: Obtendo dados brutos da API");
			String jsonBruto = consumoApi.obterDados(url);
			
			System.out.println("✅ Resposta recebida!");
			System.out.println("📊 Tamanho da resposta: " + jsonBruto.length() + " caracteres");
			System.out.println("🔍 Primeiros 200 caracteres:");
			System.out.println("─".repeat(50));
			System.out.println(jsonBruto.substring(0, Math.min(200, jsonBruto.length())));
			System.out.println("─".repeat(50));
			
			if (jsonBruto.isEmpty()) {
				System.out.println("❌ ERRO: API retornou resposta vazia!");
				return;
			}
			
			// 2. Verificar se é JSON válido
			System.out.println("\n📋 ETAPA 2: Verificando estrutura JSON");
			if (jsonBruto.contains("count") && jsonBruto.contains("results")) {
				System.out.println("✅ JSON válido detectado (contém 'count' e 'results')");
			} else {
				System.out.println("⚠️  Estrutura JSON inesperada");
				System.out.println("Conteúdo contém 'count': " + jsonBruto.contains("count"));
				System.out.println("Conteúdo contém 'results': " + jsonBruto.contains("results"));
			}
			
			// 3. Mapear para objetos Java
			System.out.println("\n🔄 ETAPA 3: Mapeando JSON para objetos Java");
			RespostaApi resposta = consumoApi.obterDadosMapeados(url);
			
			System.out.println("✅ Mapeamento realizado com sucesso!");
			System.out.println("📚 Total de livros na API: " + resposta.total());
			System.out.println("📖 Livros nesta página: " + resposta.livros().size());
			
			// 4. Mostrar detalhes dos primeiros livros
			System.out.println("\n📖 ETAPA 4: Detalhes dos primeiros livros");
			resposta.livros().stream()
				.limit(3)
				.forEach(livro -> {
					System.out.println("─".repeat(40));
					System.out.println("📖 ID: " + livro.id());
					System.out.println("📚 Título: " + livro.titulo());
					System.out.println("👥 Autores: " + livro.autores().size());
					if (!livro.autores().isEmpty()) {
						System.out.println("   • " + livro.autores().get(0).nome());
					}
					System.out.println("🌍 Idiomas: " + String.join(", ", livro.idiomas()));
					System.out.println("⬇️  Downloads: " + livro.downloads());
				});
			
			System.out.println("\n🎉 === SUCESSO! API funcionando perfeitamente ===\n");
			
		} catch (Exception e) {
			System.err.println("\n💥 === ERRO DURANTE EXECUÇÃO ===");
			System.err.println("❌ Tipo do erro: " + e.getClass().getSimpleName());
			System.err.println("❌ Mensagem: " + e.getMessage());
			System.err.println("\n📋 Stack trace completo:");
			e.printStackTrace();
		}
	}
}



