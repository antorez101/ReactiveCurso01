package com.reactor.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Comentarios {
	
	List<String> comentarios;
	
	public Comentarios() {
		this.comentarios = new ArrayList<String>();
	}
	
	
	public void addComentario(String comentario) {
		this.comentarios.add(comentario);
	}


	@Override
	public String toString() {
		return "Comentarios comentarios=" + comentarios;
	}
	
	
	

}
