package com.reactor.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ObjectUtils;

import com.reactor.demo.model.Comentarios;
import com.reactor.demo.model.Usuario;
import com.reactor.demo.model.UsuarioComentario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class DemoReactorApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(DemoReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//transform();
		//transformFlatMap();
		//transformFToListlatMap();
		//transformToMonoListlatMap();
		//mergeWithFlatMap();
		//mergeWithZipWith();
		//mergeWithZipWithForm2();
		rangesWithZipWith();
		
	}
	
	
	public void transform() {
		
		Flux<String> names = Flux.just("Marco", "Sofi", "Amaia", "Maria")
				.map(name -> new Usuario(name, ""))
				.doOnNext(usuario -> {
					if (null == usuario) {
						throw new RuntimeException("Name can't be empty");
					}
					System.out.println(usuario);
				})
				.map(usuario -> usuario.getName().toUpperCase())
				.filter(name -> name.startsWith("M"));
		
		names.subscribe(name -> log.info(name), error -> log.error(error.getMessage()), new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Flux completed");
			}
		});
		
	}
	
	public void transformFlatMap() {
		
		Flux<Usuario> usuarios = Flux.fromIterable(usuarios());
		
		Flux<String> names = 				
				usuarios.doOnNext(usuario -> {
					if (null == usuario) {
						throw new RuntimeException("Name can't be empty");
					}
					System.out.println(usuario);
				})				
				.flatMap(usuario -> {
					if (usuario.getName().startsWith("P")) {
						return Mono.just(usuario.getName().toUpperCase());
					}
					return Mono.empty();
					});
		
		names.subscribe(name -> log.info(name), error -> log.error(error.getMessage()), new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Flux completed");
			}
		});
		
	}
	
	public void mergeWithFlatMap() {
		
		Mono<Usuario> monoUsuario = Mono.fromCallable(() -> new Usuario("Marco", "Hernandez"));
		Mono<Comentarios> monoComentarios = Mono.fromCallable(() -> {			
			Comentarios comentarios = new Comentarios();
			comentarios.addComentario("Comentario uno");
			comentarios.addComentario("Comentario dos");
			comentarios.addComentario("Comentario tres");
			return comentarios;
		});
		
		monoUsuario.flatMap(u -> monoComentarios.map(c -> new UsuarioComentario(c, u) ) ).subscribe(uc -> log.info(uc.toString()));
		
	}
	
	public void mergeWithZipWith() {
		
		Mono<Usuario> monoUsuario = Mono.fromCallable(() -> new Usuario("Marco", "Hernandez"));
		Mono<Comentarios> monoComentarios = Mono.fromCallable(() -> {			
			Comentarios comentarios = new Comentarios();
			comentarios.addComentario("Comentario uno");
			comentarios.addComentario("Comentario dos");
			comentarios.addComentario("Comentario tres");
			return comentarios;
		});
		
		Mono<UsuarioComentario> usuarioComentario = monoUsuario.zipWith(monoComentarios, (usuario, comments) -> new UsuarioComentario(comments, usuario));
		usuarioComentario.subscribe(uc -> log.info(uc.toString()));
		
	}
	
	public void mergeWithZipWithForm2() {
		
		Mono<Usuario> monoUsuario = Mono.fromCallable(() -> new Usuario("Marco", "Hernandez"));
		Mono<Comentarios> monoComentarios = Mono.fromCallable(() -> {			
			Comentarios comentarios = new Comentarios();
			comentarios.addComentario("Comentario uno");
			comentarios.addComentario("Comentario dos");
			comentarios.addComentario("Comentario tres");
			return comentarios;
		});
		
		Mono<UsuarioComentario> usuarioComentario = monoUsuario.zipWith(monoComentarios).map(tuple -> 
			new UsuarioComentario(tuple.getT2(), tuple.getT1())
		);
		usuarioComentario.subscribe(uc -> log.info(uc.toString()));
		
	}
	
	public void rangesWithZipWith() {
		
		Flux<Integer> rango = Flux.range(0, 4);
		
		Flux<String> numeros = Flux.just(1,2,3,4)
				.map(num -> num * 2)
				.zipWith(rango, (number, r) -> String.format("NUM %d - %d", number, r));
		
		numeros.subscribe(nums -> log.info(nums));
		
	}
	
	public void transformFToListlatMap() {
		
		Flux.fromIterable(usuarios()).subscribe(usuario -> log.info(usuario.toString()));		
		
	}
	
	public void transformToMonoListlatMap() {
		
		Flux.fromIterable(usuarios()).collectList().subscribe(usuario -> log.info(usuario.toString()));		
		
	}

	
	public List<Usuario> usuarios(){
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(new Usuario("Marco", "Hernandez"));
		usuarios.add(new Usuario("Sofia", "Hernandez"));
		usuarios.add(new Usuario("Amaia", "Hernandez"));
		usuarios.add(new Usuario("Pedro", "Torres"));
		usuarios.add(new Usuario("Paco", "Malgesto"));
		return usuarios;
	}
	

}
