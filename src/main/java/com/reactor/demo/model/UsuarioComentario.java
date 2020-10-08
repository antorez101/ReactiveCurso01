package com.reactor.demo.model;

public class UsuarioComentario {
	
	Comentarios comentarios;
	Usuario usuario;
	
	public UsuarioComentario(Comentarios comentarios, Usuario usuario) {
		this.usuario = usuario;
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "UsuarioComentario [comentarios=" + comentarios + ", usuario=" + usuario + "]";
	}
	
	

}
